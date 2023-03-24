package top.yueshushu.learn.extension.business.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.ExtJobInfoType;
import top.yueshushu.learn.enumtype.message.ExtInterfaceTemplateType;
import top.yueshushu.learn.extension.business.ExtFastingBusiness;
import top.yueshushu.learn.extension.business.ExtJobInfoBusiness;
import top.yueshushu.learn.extension.domain.ExtInterfaceDo;
import top.yueshushu.learn.extension.domainservice.ExtInterfaceDomainService;
import top.yueshushu.learn.extension.entity.ExtCustomer;
import top.yueshushu.learn.extension.entity.ExtCustomerJob;
import top.yueshushu.learn.extension.entity.ExtJobInfo;
import top.yueshushu.learn.extension.model.ro.ExtJobInfoRo;
import top.yueshushu.learn.extension.service.ExtCustomerJobService;
import top.yueshushu.learn.extension.service.ExtCustomerService;
import top.yueshushu.learn.extension.service.ExtJobInfoService;
import top.yueshushu.learn.extension.service.ExtTemplateTypeService;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.util.CronExpression;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-02
 */
@Service
@Slf4j
public class ExtJobInfoBusinessImpl implements ExtJobInfoBusiness {
    @Resource
    private ExtJobInfoService extJobInfoService;
    @Resource
    private WeChatService weChatService;
    @Resource
    private ExtCustomerJobService extCustomerJobService;
    @Resource
    private ExtInterfaceDomainService extInterfaceDomainService;

    @SuppressWarnings("all")
    @Resource(name = Const.ASYNC_SERVICE_EXECUTOR_BEAN_NAME)
    private AsyncTaskExecutor executor;
    @Resource
    private ExtTemplateTypeService extTemplateTypeService;
    @Resource
    private ExtCustomerService extCustomerService;
    @Resource
    private ExtFastingBusiness extFastingBusiness;


    @Override
    public OutputResult listJob(ExtJobInfoRo extJobInfoRo) {
        return extJobInfoService.pageJob(extJobInfoRo);
    }

    @Override
    public OutputResult changeStatus(Integer id, DataFlagType dataFlagType) {
        return extJobInfoService.changeStatus(id, dataFlagType);
    }

    @Override
    public OutputResult execJob(ExtJobInfoType jobInfoType, Integer triggerType) {
        if (jobInfoType == null) {
            return OutputResult.buildAlert(ResultCode.NO_AUTH);
        }
        if (ExtJobInfoType.FASTING.equals(jobInfoType)) {
            return execOtherNoInterface(jobInfoType, triggerType);
        }
        //查询任务
        ExtJobInfo jobInfo = extJobInfoService.getByCode(jobInfoType);
        if (jobInfo == null) {
            log.info("当前任务 {} 已经被删除", jobInfoType.getDesc());
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        // 禁用状态，不执行。
        if (!DataFlagType.NORMAL.getCode().equals(jobInfo.getTriggerStatus())) {
            log.info(">>当前任务 {}是禁用状态，不执行", jobInfoType.getDesc());
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        jobInfo.setTriggerLastTime(DateUtil.date().toLocalDateTime());
        //查询一下，当前任务所关联的用户对象列表.
        List<ExtCustomerJob> extCustomerJobList = extCustomerJobService.listByJobId(jobInfo.getId());
        if (CollectionUtils.isEmpty(extCustomerJobList)) {
            log.info(">>>>未查询出关联的用户");
            return OutputResult.buildSucc();
        }
        try {

            // 按照用户进行分组
            Map<Integer, List<ExtCustomerJob>> customerInterfaceMap = extCustomerJobList.stream()
                    .collect(Collectors.groupingBy(ExtCustomerJob::getExtCustomerId));

            //获取所有的接口，并进行去重
            List<Integer> interfaceIdList = extCustomerJobList.stream().map(ExtCustomerJob::getExtInterfaceId).distinct().collect(Collectors.toList());

            List<ExtInterfaceTemplateType> extJobInfoTypeList = convertByIdList(interfaceIdList);
            // 去异步调用，每一个都获取信息.
            if (CollectionUtils.isEmpty(extCustomerJobList)) {
                log.info(">>>>未查询出相应的接口");
                return OutputResult.buildSucc();
            }
            //这里面获取响应信息了.
            Map<String, String> interfaceResponseMap = getResponse(extJobInfoTypeList);

            // 开始针对用户进行处理.
            Map<ExtCustomer, List<String>> convertCustomerInterfaceListMap = convertCustomerMap(customerInterfaceMap);

            // 查询出，用户和及相关的响应信息
            for (Map.Entry<ExtCustomer, List<String>> entry : convertCustomerInterfaceListMap.entrySet()) {
                // 查询出用户昵称
                ExtCustomer extCustomerDo = entry.getKey();
                if (extCustomerDo == null) {
                    continue;
                }
                List<String> interfaceCodeList = entry.getValue();
                // 获取响应信息.
                List<String> sendMessagetList = new ArrayList<>();
                StringBuilder stringBuilder = new StringBuilder();
                for (String interfaceCode : interfaceCodeList) {
                    // 查询对应的信息
                    String interfaceResponse = interfaceResponseMap.getOrDefault(interfaceCode, "");
                    //如果是早安，或者是早安的话
                    if (ExtInterfaceTemplateType.ZAOAN.getCode().equals(interfaceCode)
                            || ExtInterfaceTemplateType.WANAN.getCode().equals(interfaceCode)) {
                        if (!StringUtils.isEmpty(interfaceCode)) {
                            interfaceResponse = interfaceResponse.replaceFirst("我的朋友", extCustomerDo.getName());
                        }
                    }
                    // 获取当前字符串的信息
                    if (stringBuilder.length() + interfaceResponse.length() > 900) {
                        //进行重置.
                        sendMessagetList.add(stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                    }
                    stringBuilder.append(interfaceResponse);
                }
                //进行重置.
                sendMessagetList.add(stringBuilder.toString());
                //对响应的集合，进行处理.
                for (String sendMessage : sendMessagetList) {
                    weChatService.sendTextMessageBySign(extCustomerDo.getWxId(), sendMessage);
                }
            }
            jobInfo.setTriggerLastResult(1);
            jobInfo.setTriggerLastErrorMessage(null);
        } catch (Exception e) {
            jobInfo.setTriggerLastResult(0);
            jobInfo.setTriggerLastErrorMessage(e.getMessage());
            //执行任务失败，会发送消息到当前的用户.
            String errorWxMessage = MessageFormat.format("执行任务 {0} 失败，失败原因是:{1}",
                    jobInfoType.getDesc(), e.getMessage());
            weChatService.sendTextMessage(Const.DEFAULT_USER_ID, errorWxMessage);
            log.error("执行任务失败{}", e);
        } finally {
            //设置下次触发的时间
            jobInfo.setTriggerType(triggerType);
            jobInfo.setTriggerLastTime(LocalDateTime.now());
            try {
                CronExpression cronExpression = new CronExpression(jobInfo.getCron());
                Date date = cronExpression.getNextValidTimeAfter(new Date());
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                jobInfo.setTriggerNextTime(localDateTime);
            } catch (Exception e) {
                log.error("获取下一次触发时间失败", e);
            }
            extJobInfoService.updateInfoById(jobInfo);
        }
        if (StringUtils.isEmpty(jobInfo.getTriggerLastErrorMessage())) {
            return OutputResult.buildSucc();
        } else {
            return OutputResult.buildFail(jobInfo.getTriggerLastErrorMessage());
        }
    }


    /**
     * 将其转换成用户昵称和 对应的接口 code码的形式
     *
     * @param customerInterfaceMap 源 用户id -->接口id 集合
     * @return 将其转换成用户昵称和 对应的接口 code码的形式
     */
    private Map<ExtCustomer, List<String>> convertCustomerMap(Map<Integer, List<ExtCustomerJob>> customerInterfaceMap) {
        //1. 先查询出所有的用户
        List<ExtCustomer> extCustomerDoList = extCustomerService.findAll();
        //转换成map
        Map<Integer, ExtCustomer> extCustomerDoMap = extCustomerDoList.stream()
                .collect(Collectors.toMap(ExtCustomer::getId, n -> n));
        //2. 查询出接口
        List<ExtInterfaceDo> extInterfaceDoList = extInterfaceDomainService.list();
        //转换成map
        Map<Integer, ExtInterfaceDo> extInterfaceDoMap = extInterfaceDoList.stream()
                .collect(Collectors.toMap(ExtInterfaceDo::getId, n -> n));


        Map<ExtCustomer, List<String>> result = new HashMap<>(customerInterfaceMap.size());
        for (Map.Entry<Integer, List<ExtCustomerJob>> entry : customerInterfaceMap.entrySet()) {
            Integer customerId = entry.getKey();
            List<Integer> interfaceIdList = entry.getValue().stream().map(ExtCustomerJob::getExtInterfaceId).collect(Collectors.toList());
            //客户信息
            ExtCustomer extCustomerDo = extCustomerDoMap.get(customerId);
            // 查询出对应的编码信息
            result.put(extCustomerDo, extInterfaceIdToCode(interfaceIdList, extInterfaceDoMap));
        }
        return result;
    }

    private List<String> extInterfaceIdToCode(List<Integer> integerIdList,
                                              Map<Integer, ExtInterfaceDo> extInterfaceDoMap) {
        //接下来，进行处理
        List<String> result = new ArrayList<>();
        for (Integer interfaceId : integerIdList) {
            ExtInterfaceDo tempDo = extInterfaceDoMap.get(interfaceId);
            if (null == tempDo) {
                continue;
            }
            ExtInterfaceTemplateType tempType = ExtInterfaceTemplateType.getInterfaceType(tempDo.getCode());
            if (null == tempType) {
                continue;
            }
            result.add(tempType.getCode());
        }
        return result;
    }

    private Map<String, String> getResponse(List<ExtInterfaceTemplateType> extJobInfoTypeList) {
        Map<String, String> responseMap = Collections.synchronizedMap(new HashMap<>(extJobInfoTypeList.size()));
        CountDownLatch countDownLatch = new CountDownLatch(extJobInfoTypeList.size());
        // 股票信息展示
        for (ExtInterfaceTemplateType templateType : extJobInfoTypeList) {
            executor.submit(
                    () -> {
                        try {
                            Map<String, String> typeMap = extTemplateTypeService.getResponseByType(templateType);
                            responseMap.putAll(typeMap);
                        } catch (Exception e) {

                        } finally {
                            countDownLatch.countDown();
                        }
                    }
            );
        }
        try {
            countDownLatch.await(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("异常信息", e);
        }
        return responseMap;
    }

    /**
     * 获取对应的列表信息
     *
     * @param interfaceIdList 接口id集合
     * @return 返回对应的列表信息
     */
    private List<ExtInterfaceTemplateType> convertByIdList(List<Integer> interfaceIdList) {
        List<ExtInterfaceDo> extInterfaceDoList = extInterfaceDomainService.list();
        //转换成map
        Map<Integer, ExtInterfaceDo> extInterfaceDoMap = extInterfaceDoList.stream()
                .collect(Collectors.toMap(ExtInterfaceDo::getId, n -> n));
        //接下来，进行处理
        List<ExtInterfaceTemplateType> result = new ArrayList<>();
        for (Integer interfaceId : interfaceIdList) {
            ExtInterfaceDo tempDo = extInterfaceDoMap.get(interfaceId);
            if (null == tempDo) {
                continue;
            }
            ExtInterfaceTemplateType tempType = ExtInterfaceTemplateType.getInterfaceType(tempDo.getCode());
            if (null == tempType) {
                continue;
            }
            result.add(tempType);
        }
        return result;
    }


    private OutputResult execOtherNoInterface(ExtJobInfoType jobInfoType, Integer triggerType) {
        if (jobInfoType == null) {
            return OutputResult.buildAlert(ResultCode.NO_AUTH);
        }
        //查询任务
        ExtJobInfo jobInfo = extJobInfoService.getByCode(jobInfoType);
        if (jobInfo == null) {
            log.info("当前任务 {} 已经被删除", jobInfoType.getDesc());
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        // 禁用状态，不执行。
        if (!DataFlagType.NORMAL.getCode().equals(jobInfo.getTriggerStatus())) {
            log.info(">>当前任务 {}是禁用状态，不执行", jobInfoType.getDesc());
            return OutputResult.buildAlert(ResultCode.JOB_ID_NOT_EXIST);
        }
        jobInfo.setTriggerLastTime(DateUtil.date().toLocalDateTime());
        try {
            //当前的年
            //查询一下，当前任务所关联的用户对象列表.
            List<ExtCustomerJob> extCustomerJobList = extCustomerJobService.listByJobId(jobInfo.getId());
            if (CollectionUtils.isEmpty(extCustomerJobList)) {
                log.info(">>>>未查询出关联的用户");
                return OutputResult.buildSucc();
            }
            //获取所有的接口，并进行去重
            List<Integer> interfaceIdList = extCustomerJobList.stream()
                    .map(ExtCustomerJob::getExtInterfaceId)
                    .distinct().collect(Collectors.toList());
            // 只获取第一个接口即可.
            Integer jobRelationInterfaceId = interfaceIdList.get(0);
            String result = "";
            switch (jobInfoType) {
                case FASTING: {
                    result = extFastingBusiness.fastingJob(jobRelationInterfaceId);
                    break;
                }
                default: {
                    break;
                }

            }
            //不为空的话，注意发送消息
            if (!StringUtils.isEmpty(result)) {
                List<Integer> extCustomerIdList = extCustomerJobList.stream()
                        .map(ExtCustomerJob::getExtCustomerId)
                        .distinct().collect(Collectors.toList());
                //查询对应的客户信息
                //1. 先查询出所有的用户
                List<ExtCustomer> extCustomerDoList = extCustomerService.findAll();
                //转换成map
                Map<Integer, ExtCustomer> extCustomerDoMap = extCustomerDoList.stream()
                        .collect(Collectors.toMap(ExtCustomer::getId, n -> n));
                for (Integer customerId : extCustomerIdList) {
                    ExtCustomer extCustomer = extCustomerDoMap.get(customerId);
                    if (extCustomer != null) {
                        String content = "你好," + extCustomer.getName() + System.lineSeparator();
                        weChatService.sendTextMessageBySign(extCustomer.getWxId(), content + result);
                    }
                }
            }
            jobInfo.setTriggerLastResult(1);
            jobInfo.setTriggerLastErrorMessage(null);
        } catch (Exception e) {
            jobInfo.setTriggerLastResult(0);
            jobInfo.setTriggerLastErrorMessage(e.getMessage());
            //执行任务失败，会发送消息到当前的用户.
            String errorWxMessage = MessageFormat.format("执行任务 {0} 失败，失败原因是:{1}",
                    jobInfoType.getDesc(), e.getMessage());
            weChatService.sendTextMessage(Const.DEFAULT_USER_ID, errorWxMessage);
            log.error("执行任务失败{}", e);
        } finally {
            //设置下次触发的时间
            jobInfo.setTriggerType(triggerType);
            jobInfo.setTriggerLastTime(LocalDateTime.now());
            try {
                CronExpression cronExpression = new CronExpression(jobInfo.getCron());
                Date date = cronExpression.getNextValidTimeAfter(new Date());
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                jobInfo.setTriggerNextTime(localDateTime);
            } catch (Exception e) {
                log.error("获取下一次触发时间失败", e);
            }
            extJobInfoService.updateInfoById(jobInfo);
        }
        if (StringUtils.isEmpty(jobInfo.getTriggerLastErrorMessage())) {
            return OutputResult.buildSucc();
        } else {
            return OutputResult.buildFail(jobInfo.getTriggerLastErrorMessage());
        }
    }

    @Override
    public OutputResult deleteById(Integer id) {
        return extJobInfoService.deleteById(id);
    }

    @Override
    public OutputResult handlerById(Integer id) {
        //立即执行
        ExtJobInfo job = extJobInfoService.getById(id);
        OutputResult outputResult = execJob(ExtJobInfoType.getJobInfoType(job.getCode()), EntrustType.HANDLER.getCode());
        return outputResult;
    }
}
