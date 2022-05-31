package top.yueshushu.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.enumtype.ExchangeType;
import top.yueshushu.learn.enumtype.NameOperationType;
import top.yueshushu.learn.enumtype.TradingAreaType;
import top.yueshushu.learn.mode.dto.PoundageCalcDto;
import top.yueshushu.learn.mode.dto.PoundageDto;
import top.yueshushu.learn.mode.ro.MoneyRo;
import top.yueshushu.learn.mode.vo.CalcBuyMoneyVo;
import top.yueshushu.learn.mode.vo.CalcMoneyVo;
import top.yueshushu.learn.mode.vo.CalcSellMoneyVo;
import top.yueshushu.learn.mode.vo.MoneyVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.MoneyService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.MathUtil;
import top.yueshushu.learn.util.PoundageCalcUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:MoneyServiceImpl
 * @Description 金额的实现类
 * @Author 岳建立
 * @Date 2021/11/6 16:01
 * @Version 1.0
 **/
@Service
@Slf4j
public class MoneyServiceImpl implements MoneyService {
    @Override
    public Double handlerClearPrice(MoneyRo moneyRo) {
        return handlerPrice(moneyRo);
    }

    @Override
    public MoneyVo assemblyClearMoneyVo(MoneyRo moneyRo) {
        return assemblyMoneyVo(moneyRo);
    }

    @Override
    public CalcMoneyVo reduceCalcMoneyVo(MoneyRo moneyRo) {
        CalcMoneyVo result=new CalcMoneyVo();
        //填充基本的信息
        result.setCode(moneyRo.getCode());
        result.setName("暂时不实现");
        //填写买入的信息
        reduceCalcBuyMoneyList(result,moneyRo);
        //没有卖出,为空
        reduceCalcSellMoneyList(result,moneyRo);
        //处理总计
        calcReduceMoneyVo(result);
        result.setNowOperationPrice(String.valueOf(moneyRo.getSecPrice()));
        return result;

    }

    private void calcReduceMoneyVo(CalcMoneyVo result) {
        List<CalcBuyMoneyVo> calcBuyMoneyVoList = result.getCalcBuyMoneyVoList();
        List<CalcSellMoneyVo> calcSellMoneyVoList = result.getCalcSellMoneyVoList();


        //计算总的买入费用
        BigDecimal totalBuyCharge= SystemConst.DEFAULT_EMPTY;
        BigDecimal totalSellCharge=SystemConst.DEFAULT_EMPTY;
        BigDecimal totalCharge=SystemConst.DEFAULT_EMPTY;
        int totalNum=0;
        BigDecimal totalMoney=SystemConst.DEFAULT_EMPTY;
        for(CalcBuyMoneyVo calcBuyMoneyVo:calcBuyMoneyVoList){
            totalBuyCharge= totalBuyCharge.add(
                    BigDecimalUtil.toBigDecimal( calcBuyMoneyVo.getTotalBuyCharge())
            );
            totalNum+=calcBuyMoneyVo.getNumber();
            totalMoney=totalMoney.add(
                    BigDecimalUtil.toBigDecimal(
                            calcBuyMoneyVo.getPrice(),
                            calcBuyMoneyVo.getNumber()+""
                    )
            );
        }

        for(CalcSellMoneyVo calcSellMoneyVo:calcSellMoneyVoList){
            totalSellCharge= totalSellCharge.add(
                    BigDecimalUtil.toBigDecimal( calcSellMoneyVo.getTotalSellCharge())
            );
            totalNum-=calcSellMoneyVo.getNumber();
            totalMoney=totalMoney.subtract(
                    BigDecimalUtil.toBigDecimal(
                            calcSellMoneyVo.getPrice(),
                            calcSellMoneyVo.getNumber()+""
                    )
            );
        }
        totalCharge=BigDecimalUtil.addBigDecimal(totalSellCharge,totalBuyCharge);
        result.setTotalBuyCharge(BigDecimalUtil.toString(totalBuyCharge));
        result.setTotalSellCharge(BigDecimalUtil.toString(totalSellCharge));
        result.setTotalCharge(BigDecimalUtil.toString(totalCharge));
        result.setTotalNum(totalNum);
        result.setTotalMoney(BigDecimalUtil.toString(totalMoney));
        //处理平均价格
        if(totalNum==0){
            result.setAvgPrice(BigDecimalUtil.toString(
                    SystemConst.DEFAULT_EMPTY
            ));
        }else{
            result.setAvgPrice(
                    BigDecimalUtil.divBigDecimal(totalMoney,
                            new BigDecimal(totalNum))
            );
        }

    }

    private void reduceCalcSellMoneyList(CalcMoneyVo result, MoneyRo moneyRo) {
        //计算买入的费用
        Double secondPrice = moneyRo.getSecPrice();
        int secondNumber=moneyRo.getSecNumber();
        List<CalcSellMoneyVo> calcSellMoneyVoList=new ArrayList<>();
        calcSellMoneyVoList.add(getCalcSellMoneyVo(moneyRo,secondPrice,secondNumber));
        result.setCalcSellMoneyVoList(calcSellMoneyVoList);

    }

    private CalcSellMoneyVo getCalcSellMoneyVo(MoneyRo moneyRo, Double price, int num) {

        PoundageDto poundageDto=new PoundageDto();
        poundageDto.setPlatformFee(BigDecimalUtil.toBigDecimal(moneyRo.getPlatformFee()));
        poundageDto.setTradingAreaType(TradingAreaType.getExchangeType(moneyRo.getTradingArea()));
        poundageDto.setBuyPrice(BigDecimalUtil.toBigDecimal(price));
        poundageDto.setSellPrice(BigDecimalUtil.toBigDecimal(price));
        poundageDto.setBuyNumber(num);
        poundageDto.setSellNumber(num);
        poundageDto.setExchangeType(ExchangeType.prefix(moneyRo.getCode().substring(0,2)));
        poundageDto.setNameOperationType(NameOperationType.getNameType(moneyRo.getNameType()));
        //第一次买入的计算结果
        PoundageCalcDto calc = PoundageCalcUtil.calc(poundageDto);

        CalcSellMoneyVo result=new CalcSellMoneyVo();
        result.setPrice(String.valueOf(price));
        result.setNumber(num);
        result.setSellStampDuty(BigDecimalUtil.toString(calc.getSellStampDuty()));
        result.setSellCharge(BigDecimalUtil.toString(calc.getSellCharge()));
        result.setSellTransferFee(BigDecimalUtil.toString(calc.getSellTransferFee()));
        result.setSellCommunications(BigDecimalUtil.toString(calc.getSellCommunications()));
        result.setTotalSellCharge(BigDecimalUtil.toString(calc.getTotalSellCharge()));
        result.setSellMoney(
            BigDecimalUtil.toString(
                   BigDecimalUtil.subBigDecimal(
                           BigDecimalUtil.toBigDecimal(
                                   price,Double.valueOf(
                                           num+""
                                   )
                           ),
                           calc.getTotalSellCharge()
                   )
            )
        );
        return result;
    }

    private void reduceCalcBuyMoneyList(CalcMoneyVo result, MoneyRo moneyRo) {
        //计算买入的费用
        Double firstPrice = moneyRo.getPrice();
        int firstNum=moneyRo.getNumber();
        List<CalcBuyMoneyVo> calcBuyMoneyVoList=new ArrayList<>();
        calcBuyMoneyVoList.add(getCalcBuyMoneyVo(moneyRo,firstPrice,firstNum));
        result.setCalcBuyMoneyVoList(calcBuyMoneyVoList);
    }

    @Override
    public Double handlerReducePrice(MoneyRo moneyRo) {
        Double result=0.0d;
        switch (moneyRo.getType()){
            case 1:{
                result=moneyRo.getSecPrice();
                break;
            }
            case 2:{
                result=reduceMoneyToPrice(moneyRo);
                break;
            }
            default:{
                break;
            }
        }
        return result;


    }

    private Double reduceMoneyToPrice(MoneyRo moneyRo) {
        /**
         * 平均*总数量=总价钱=第1价格-第2价钱
         * 第二价钱= 第一价格-平均*总数量
         * 第二价格= 第二价钱/第二数量
         *
         */
        //现在想要的总的价钱
        BigDecimal totalPrice= BigDecimalUtil.toBigDecimal(
                moneyRo.getMakePrice(),Double.parseDouble(
                        String.valueOf(moneyRo.getNumber())
                )
        );
        //以前的买的第一次价格
        BigDecimal firstTotalPrice=BigDecimalUtil.toBigDecimal(
                moneyRo.getPrice(),Double.parseDouble(
                        moneyRo.getNumber()+""
                )
        );
        BigDecimal secondTotalPrice=firstTotalPrice.subtract(totalPrice);
        //这次买入需要花费的金额是
        String buyPrice=BigDecimalUtil.divBigDecimal(
                secondTotalPrice,
                new BigDecimal(moneyRo.getSecNumber())
        );
        return Double.valueOf(buyPrice);

    }


    /**
     * 计算补仓位的相关信息
     * @param moneyRo
     * @return
     */
    @Override
    public CalcMoneyVo coverCalcMoneyVo(MoneyRo moneyRo) {
        CalcMoneyVo result=new CalcMoneyVo();
        //填充基本的信息
        result.setCode(moneyRo.getCode());
        result.setName("暂时不实现");
        //填写买入的信息
        calcBuyMoneyList(result,moneyRo);
        //没有卖出,为空
        result.setCalcSellMoneyVoList(new ArrayList<>());

        //处理总计
        calcMoneyVo(result);
        result.setNowOperationPrice(String.valueOf(moneyRo.getSecPrice()));
        return result;
    }

    private void calcMoneyVo(CalcMoneyVo result) {

        List<CalcBuyMoneyVo> calcBuyMoneyVoList = result.getCalcBuyMoneyVoList();
        //计算总的买入费用
        BigDecimal totalBuyCharge=SystemConst.DEFAULT_EMPTY;
        BigDecimal totalCharge=SystemConst.DEFAULT_EMPTY;
        int totalNum=0;
        BigDecimal totalMoney=SystemConst.DEFAULT_EMPTY;
        BigDecimal avgPrice=SystemConst.DEFAULT_EMPTY;
        for(CalcBuyMoneyVo calcBuyMoneyVo:calcBuyMoneyVoList){
            totalBuyCharge= totalBuyCharge.add(
                   BigDecimalUtil.toBigDecimal( calcBuyMoneyVo.getTotalBuyCharge())
            );
            totalCharge=totalCharge.add(
                    BigDecimalUtil.toBigDecimal( calcBuyMoneyVo.getTotalBuyCharge())
            );
            totalNum+=calcBuyMoneyVo.getNumber();
            totalMoney=totalMoney.add(
                   BigDecimalUtil.toBigDecimal( calcBuyMoneyVo.getDealMoney())
            );
        }


        result.setTotalBuyCharge(BigDecimalUtil.toString(totalBuyCharge));
        result.setTotalCharge(BigDecimalUtil.toString(totalCharge));
        result.setTotalNum(totalNum);
        result.setTotalMoney(BigDecimalUtil.toString(totalMoney));
        //处理平均价格
        result.setAvgPrice(
                BigDecimalUtil.divBigDecimal(totalMoney,
                        new BigDecimal(totalNum))
        );
    }

    /**
     * 计算买入的相关信息
     * @param result
     * @param moneyRo
     */
    private void calcBuyMoneyList(CalcMoneyVo result, MoneyRo moneyRo) {
        //计算买入的费用
        Double firstPrice = moneyRo.getPrice();
        int firstNum=moneyRo.getNumber();
        //第二次的操作
        Double secondPrice=moneyRo.getSecPrice();
        int secondNum=moneyRo.getSecNumber();

        //
        List<CalcBuyMoneyVo> calcBuyMoneyVoList=new ArrayList<>();
        calcBuyMoneyVoList.add(getCalcBuyMoneyVo(moneyRo,firstPrice,firstNum));
        calcBuyMoneyVoList.add(getCalcBuyMoneyVo(moneyRo,secondPrice,secondNum));
        result.setCalcBuyMoneyVoList(calcBuyMoneyVoList);
    }

    public CalcBuyMoneyVo getCalcBuyMoneyVo(MoneyRo moneyRo,Double price,int num){
        PoundageDto poundageDto=new PoundageDto();
        poundageDto.setPlatformFee(BigDecimalUtil.toBigDecimal(moneyRo.getPlatformFee()));
        poundageDto.setTradingAreaType(TradingAreaType.getExchangeType(moneyRo.getTradingArea()));
        poundageDto.setBuyPrice(BigDecimalUtil.toBigDecimal(price));
        poundageDto.setSellPrice(BigDecimalUtil.toBigDecimal(price));
        poundageDto.setBuyNumber(num);
        poundageDto.setSellNumber(num);
        poundageDto.setExchangeType(ExchangeType.prefix(moneyRo.getCode().substring(0,2)));
        poundageDto.setNameOperationType(NameOperationType.getNameType(moneyRo.getNameType()));
        //第一次买入的计算结果
        PoundageCalcDto calc = PoundageCalcUtil.calc(poundageDto);

        CalcBuyMoneyVo result=new CalcBuyMoneyVo();
        result.setPrice(String.valueOf(price));
        result.setNumber(num);
        result.setBuyCharge(BigDecimalUtil.toString(calc.getBuyCharge()));
        result.setBuyTransferFee(BigDecimalUtil.toString(calc.getBuyTransferFee()));
        result.setBuyCommunications(BigDecimalUtil.toString(calc.getBuyCommunications()));
        result.setTotalBuyCharge(BigDecimalUtil.toString(calc.getTotalBuyCharge()));
        result.setDealMoney(
                BigDecimalUtil.toString(
                        BigDecimalUtil.toBigDecimal(
                                price,Double.valueOf(num+"")
                        )
                )
        );
        //买入的交易费
        result.setBuyMoney(
                BigDecimalUtil.toString(
                        calc.getTotalBuyCharge().add(
                                BigDecimalUtil.toBigDecimal(
                                        price,Double.valueOf(num+"")
                                )
                        )
                )
        );
        return result;
    }

    /**
     * 处理补仓位的价格
     * @param moneyRo
     * @return
     */
    @Override
    public Double handlerCovertPrice(MoneyRo moneyRo) {
        Double result=0.0d;
        switch (moneyRo.getType()){
            case 1:{
                result=moneyRo.getSecPrice();
                break;
            }
            case 2:{
                result=coverMoneyToPrice(moneyRo);
                break;
            }
            default:{
                break;
            }
        }
        return result;

    }
    /**
     * 补仓位的最后价格转换成相应的买入价格
     * @param moneyRo
     * @return
     */
    private Double coverMoneyToPrice(MoneyRo moneyRo) {
        /**
         * 平均*总数量=总价钱=第1价格+第2价钱
         * 第二价钱= 平均*总数量-第一价格
         * 第二价格= 第二价钱/第二数量
         *
         */
        //现在想要的总的价钱
        BigDecimal totalPrice= BigDecimalUtil.toBigDecimal(
                moneyRo.getMakePrice(),Double.parseDouble(
                        String.valueOf(
                                moneyRo.getNumber()
                                +moneyRo.getSecNumber()
                        )
                )
        );
        //以前的买的第一次价格
        BigDecimal firstTotalPrice=BigDecimalUtil.toBigDecimal(
                moneyRo.getPrice(),Double.parseDouble(
                        moneyRo.getNumber()+""
                )
        );
        BigDecimal secondTotalPrice=totalPrice.subtract(firstTotalPrice);
        //这次买入需要花费的金额是
        String buyPrice=BigDecimalUtil.divBigDecimal(
                secondTotalPrice,
                new BigDecimal(moneyRo.getSecNumber())
        );
        return Double.valueOf(buyPrice);
    }


    /**
     * 返回预期的清仓价格
     * @param moneyRo 价格对象
     * @return 返回预期的清仓价格
     */
    private Double handlerPrice(MoneyRo moneyRo) {
        Double result=0.0d;
        switch (moneyRo.getType()){
            case 2:{
                result=moneyRo.getMakePrice();
                break;
            }
            case 1:{
                result=moneyToPrice(moneyRo);
                break;
            }
            case 3:{
                result=patternToPrice(moneyRo);
                break;
            }
            default:{
                break;
            }
        }
        return result;
    }

    /**
     * 清仓时，将比例转换成相应的价格
     * @param moneyRo 清仓时对象
     * @return 清仓时，将比例转换成相应的价格
     */
    private Double patternToPrice(MoneyRo moneyRo) {
        Double result=moneyRo.getPrice();
        //先求出比例,这是最大的比例.
        Double old = moneyRo.getMakePrice();
        //进行循环处理
        for(Double i=moneyRo.getMakeProportion();i<=100.00;i=i+ SystemConst.DEFAULT_PATTERN_STEP){
            //临时的 makePrice 即临时的期望价格
            Double temp= BigDecimalUtil.toDouble(MathUtil.getNow(moneyRo.getPrice(),i));
            //进行计算
            moneyRo.setMakePrice(temp);
            MoneyVo moneyVo=assemblyMoneyVo(moneyRo);
            if(BigDecimalUtil.toBigDecimal(moneyRo.getMakeProportion())
            .compareTo(BigDecimalUtil.toBigDecimal(moneyVo.getRealProportion()
            .replace("%","")))<=0){
                //往上走一步
                result=temp;
                break;
            }
        }
        moneyRo.setMakePrice(old);
        return result;
    }

    /**
     * 清仓时,将金额，转换成对应的卖出价格
     * @param moneyRo 清仓时的对象
     * @return 清仓时,将金额，转换成对应的卖出价格
     */
    private Double moneyToPrice(MoneyRo moneyRo) {
        Double result=moneyRo.getPrice();
        //先求出比例,这是最大的比例.
       Double maxProportion= (moneyRo.getMakeMoney()/(moneyRo.getPrice()*moneyRo.getNumber()))*100;
        Double old = moneyRo.getMakePrice();
        //进行循环处理
        for(Double i=maxProportion;i<=100.00d;i=i+SystemConst.DEFAULT_PATTERN_STEP){
            //临时的钱
            Double temp=BigDecimalUtil.toDouble(MathUtil.getNow(moneyRo.getPrice(),i));
            //进行计算
            moneyRo.setMakePrice(temp);
            MoneyVo moneyVo=assemblyMoneyVo(moneyRo);
            if(BigDecimalUtil.toBigDecimal(moneyVo.getRealMoney())
                    .compareTo(BigDecimalUtil.toBigDecimal(moneyRo.getMakeMoney()))>=0){
                //往上走一步
                result=temp;
                break;
            }

        }
        moneyRo.setMakePrice(old);
        return result;
    }
    /**
     * 将信息进行处理，转换
     * @param moneyRo 清仓信息对象
     * @return 将信息进行处理，转换
     */
    private MoneyVo assemblyMoneyVo(MoneyRo moneyRo) {
        MoneyVo result=new MoneyVo();
        //1. 组装基本的数据
        basicInfo(moneyRo,result);
        //2.计算手续费等相关的信息.
        PoundageCalcDto poundageCalcDto=calcPoundageCalcDto(moneyRo);
        //3. 封装手续费等信息
        chargeInfo(poundageCalcDto,result);
        //4. 处理计算值
        calcInfo(result);
        return result;
    }

    /**
     * 封装基本的数据信息
     * @param moneyRo 工具对象
     * @param result 返回基础信息响应结果
     */
    private void basicInfo(MoneyRo moneyRo, MoneyVo result) {
        result.setCode(moneyRo.getCode());
        result.setPrice(moneyRo.getPrice()+"");
        result.setNumber(moneyRo.getNumber());
        result.setMakeMoney(moneyRo.getMakeMoney()+"");
        result.setMakePrice(moneyRo.getMakePrice()+"");
        result.setMakeProportion(moneyRo.getMakeProportion()+"");
        result.setType(moneyRo.getType());
    }

    /**
     * 处理计算的信息
     * @param result 响应结果处理
     */
    private void calcInfo(MoneyVo result) {
        BigDecimal buyMoney=BigDecimalUtil.toBigDecimal(
                result.getPrice(),
                result.getNumber()+""
        );
        result.setBuyMoney(
                BigDecimalUtil.toString(
                        buyMoney
                )
        );
        BigDecimal sellMoney=BigDecimalUtil.toBigDecimal(
                result.getMakePrice(),
                result.getNumber()+""
        );

        result.setSellMoney(
                BigDecimalUtil.toString(
                      sellMoney
                )
        );
        BigDecimal destMoney=
                sellMoney.subtract(
                        BigDecimalUtil.toBigDecimal(result.getTotalSellCharge())
                );
        result.setDestMoney(
                BigDecimalUtil.toString(destMoney)
        );
        //实际买入发生金额
        BigDecimal buyActualMoney=
                buyMoney.add(
                        BigDecimalUtil.toBigDecimal(result.getTotalBuyCharge())
                );

        result.setBuyActualMoney(BigDecimalUtil.toString(buyActualMoney));



        BigDecimal realMoney=destMoney.subtract(buyActualMoney);
        result.setRealMoney(BigDecimalUtil.toString(realMoney));

        //不卖的话，现在的赚钱数
        BigDecimal noSellMoney=realMoney.add(
                 BigDecimalUtil.toBigDecimal(result.getTotalSellCharge())
        );
        //不卖的话，赚钱数
        result.setNoSellMoney(BigDecimalUtil.toString(noSellMoney));

        result.setRealPrice(
                BigDecimalUtil.toString(
                     new BigDecimal(result.getMakePrice())
                ));
        //比例
        result.setRealProportion(
                BigDecimalUtil.divPattern(
                        realMoney,buyActualMoney
                )
        );
    }

    /**
     * 封装手续费信息到 result Vo 里面
     * @param poundageCalcDto 手续费对象
     * @param result 封装手续费信息到 result Vo 里面
     */
    private void chargeInfo(PoundageCalcDto poundageCalcDto, MoneyVo result) {
        result.setPlatformFee(BigDecimalUtil.toString(poundageCalcDto.getPlatformFee()));
        result.setTradingArea(poundageCalcDto.getTradingAreaType().getCode());
        //买的属性
        result.setBuyCharge(BigDecimalUtil.toString(poundageCalcDto.getBuyCharge()));
        result.setBuyTransferFee(BigDecimalUtil.toString(poundageCalcDto.getBuyTransferFee()));
        result.setBuyCommunications(BigDecimalUtil.toString(poundageCalcDto.getBuyCommunications()));
        //卖的属性
        result.setSellStampDuty(BigDecimalUtil.toString(poundageCalcDto.getSellStampDuty()));
        result.setSellCharge(BigDecimalUtil.toString(poundageCalcDto.getSellCharge()));
        result.setSellTransferFee(BigDecimalUtil.toString(poundageCalcDto.getSellTransferFee()));
        result.setSellCommunications(BigDecimalUtil.toString(poundageCalcDto.getSellCommunications()));
        //合计
        result.setTotalBuyCharge(BigDecimalUtil.toString(poundageCalcDto.getTotalBuyCharge()));
        result.setTotalSellCharge(BigDecimalUtil.toString(poundageCalcDto.getTotalSellCharge()));
        result.setTotalCharge(BigDecimalUtil.toString(poundageCalcDto.getTotalCharge()));
    }

    /**
     * 获取相应的手续费信息
     * @param moneyRo 工具对象
     * @return 获取相应的手续费信息
     */
    private PoundageCalcDto calcPoundageCalcDto(MoneyRo moneyRo) {
        //1.先构建对应的 PoundageDto 信息
        PoundageDto poundageDto=new PoundageDto();
        poundageDto.setPlatformFee(BigDecimalUtil.toBigDecimal(moneyRo.getPlatformFee()));
        poundageDto.setTradingAreaType(TradingAreaType.getExchangeType(moneyRo.getTradingArea()));
        poundageDto.setBuyPrice(BigDecimalUtil.toBigDecimal(moneyRo.getPrice()));
        poundageDto.setSellPrice(BigDecimalUtil.toBigDecimal(moneyRo.getMakePrice()));
        poundageDto.setBuyNumber(moneyRo.getNumber());
        poundageDto.setSellNumber(moneyRo.getNumber());
        poundageDto.setExchangeType(ExchangeType.prefix(moneyRo.getCode().substring(0,2)));
        poundageDto.setNameOperationType(NameOperationType.getNameType(moneyRo.getNameType()));
        return PoundageCalcUtil.calc(poundageDto);
    }

    /**
     * 进行验证信息  缺少
     * @param moneyRo
     * @return
     */
    private OutputResult validateRo(MoneyRo moneyRo) {

        return null;
    }
}
