package top.yueshushu.learn.common;

import lombok.Data;

/**
 * @ClassName:ResultCode
 * @Description Tool 工具的错误码信息
 * @Author 岳建立
 * @Date 2022/5/20 20:14
 * @Version 1.0
 **/
@Data
public class ResultCode {
    /**
     * 系统层面， 20000 成交， 30000提示， 50000报错.
     */
    public static final ResultCode SUCCESS = new ResultCode(true, 20000, "操作成功");
    public static final ResultCode ALERT = new ResultCode(false, 30000, "传入信息有误");
    public static final ResultCode FAIL = new ResultCode(false, 50000, "操作失败");
    /**
     * 爬虫 提示信息：  200 10 （编排）+ 001 (三位随机)
     */
    public static final ResultCode STOCK_ASYNC_FAIL =
            new ResultCode(true, 20010001, "股票同步失败");
    public static final ResultCode STOCK_ASYNC_SUCCESS =
            new ResultCode(true, 20010002, "股票同步成功");
    public static final ResultCode STOCK_HIS_ASYNC_FAIL =
            new ResultCode(false, 20010003, "股票历史数据同步失败");
    public static final ResultCode STOCK_HIS_ASYNC_SUCCESS =
            new ResultCode(true, 20010004, "股票历史数据同步成功");
    public static final ResultCode STOCK_CODE_ERROR =
            new ResultCode(false, 20010005, "股票编码不存在");
    /**
     * 用户层提示信息：  100 10 （编排）+ 001 (三位随机)
     */
    public static final ResultCode LOGIN_EXPIRE =
            new ResultCode(false, 10010001, "登录已过期,请重新登录");
    public static final ResultCode ACCOUNT_IS_OFFLINE =
            new ResultCode(false, 10010002, "您的账号已经在另一处登录了,您被迫下线!");
    public static final ResultCode ACCOUNT_IS_EMPTY =
            new ResultCode(false, 10010003, "用户账号不能为空");
    public static final ResultCode PASSWORD_IS_EMPTY =
            new ResultCode(false, 10010004, "密码不能为空");
    public static final ResultCode READ_AGREEMENT_TRUE =
            new ResultCode(false, 10010005, "必须同意协议");
    public static final ResultCode ACCOUNT_NOT_EXIST =
            new ResultCode(false, 10010006, "用户名或者账号不正确");
    public static final ResultCode PASSWORD_INCORRECT =
            new ResultCode(false, 10010006, "用户名或者账号不正确");
    public static final ResultCode EASY_MONEY_LOGIN_ERROR =
            new ResultCode(false, 10010007, "东方财富登录失败");
    public static final ResultCode USER_EXISTS =
            new ResultCode(false, 10010008, "用户已存在");

    /**
     * 交易用户层提示信息：  100 11 （编排）+ 001 (三位随机)
     */
    public static final ResultCode TRADE_PASSWORD_IS_EMPTY =
            new ResultCode(true, 10011001, "交易用户登录密码不能为空");
    public static final ResultCode TRADE_IDENTIFY_CODE_IS_EMPTY =
            new ResultCode(false, 10011002, "验证码不能为空");
    public static final ResultCode TRADE_USER_NO_RELATION =
            new ResultCode(false, 10011003, "当前登录用户未关联交易用户");


    /**
     * 股票信息提示信息：  100 12 （编排）+ 001 (三位随机)
     */
    public static final ResultCode STOCK_CODE_IS_EMPTY =
            new ResultCode(true, 10012001, "股票编码不能为空");
    public static final ResultCode STOCK_CODE_NO_EXIST =
            new ResultCode(true, 10012002, "股票编码不存在");
    public static final ResultCode STOCK_KLINE_IS_EMPTY =
            new ResultCode(true, 10012003, "股票K线类型不能为空");
    public static final ResultCode STOCK_EXCHANGE_IS_EMPTY =
            new ResultCode(true, 10012004, "股票交易所类型不能为空");

    public static final ResultCode HISTORY_START_DATE =
            new ResultCode(true, 10012005, "开始的时间范围不能为空");
    public static final ResultCode HISTORY_END_DATE =
            new ResultCode(true, 10012006, "结束的时间范围不能为空");

    public static final ResultCode HISTORY_START_DAY_NUM =
            new ResultCode(true, 10012007, "开始的天不能为空");

    public static final ResultCode HISTORY_END_DAY_NUM =
            new ResultCode(true, 10012008, "结束的天不能为空");

    /**
     * 股票自选提示信息：  100 13 （编排）+ 001 (三位随机)
     */
    public static final ResultCode STOCK_SELECTED_EXISTS =
            new ResultCode(true, 10013001, "已经存在自选表里面，不需要重复添加");
    public static final ResultCode STOCK_SELECTED_MAX_LIMIT =
            new ResultCode(true, 10013002, "已经超过允许自选的最大数量");
    public static final ResultCode ID_IS_EMPTY =
            new ResultCode(true, 10013003, "请选择要操作的记录");

    public static final ResultCode STOCK_SELECTED_NO_RECORD =
            new ResultCode(true, 10013004, "选中的记录不存在,请刷新后再操作");
    public static final ResultCode STOCK_SELECTED_HAVE_DISABLE =
            new ResultCode(true, 10013005, "选中的记录已经被移除自选了");
    public static final ResultCode STOCK_SELECTED_NOTES_EMPTY =
            new ResultCode(true, 10013006, "编辑的笔记不能是空");

    /**
     * 假期：  100 14 （编排）+ 001 (三位随机)
     */
    public static final ResultCode HOLIDAY_EXISTS =
            new ResultCode(true, 10014001, "假期已经同步，不需要重复同步");


    /**
     * 股票工具：  100 15 （编排）+ 001 (三位随机)
     */
    public static final ResultCode TOOL_TYPE_IS_EMPTY =
            new ResultCode(true, 10015001, "请选择工具要处理的类型");
    public static final ResultCode TOOL_MAKE_MONEY_IS_EMPTY =
            new ResultCode(true, 10015002, "预期所赚金额不能为空");
    public static final ResultCode TOOL_MAKE_PRICE_IS_EMPTY =
            new ResultCode(true, 10015003, "预期价格不能为空");
    public static final ResultCode TOOL_MAKE_PROPORTION_IS_EMPTY =
            new ResultCode(true, 10015004, "预期所赚比例不能为空");
    public static final ResultCode TOOL_NUMBER_IS_EMPTY =
            new ResultCode(true, 10015005, "交易的数量不能为空");
    public static final ResultCode TOOL_NUMBER_IS_HUNDREDS =
            new ResultCode(true, 10015006, "交易的数量必须是整百数");
    public static final ResultCode TOOL_PRICE_IS_EMPTY =
            new ResultCode(true, 10015007, "交易的价格不能为空");
    public static final ResultCode TOOL_AREA_IS_EMPTY =
            new ResultCode(true, 10015008, "交易的类型不能为空");
    public static final ResultCode TOOL_FEE_IS_EMPTY =
            new ResultCode(true, 10015009, "交易的手续费不能为空");
    public static final ResultCode TOOL_TYPE_NOT_SUPPORT=
            new ResultCode(true, 10015010, "工具要处理的类型不支持");
    public static final ResultCode TOOL_FEE_NOT_SUPPORT=
            new ResultCode(true, 10015011, "交易手续费在 0.02 到 0.03之间");


    public static final ResultCode TOOL_CONVERT_NUMBER_IS_EMPTY =
            new ResultCode(true, 10015012, "第二次补仓交易的数量不能为空");
    public static final ResultCode TOOL_CONVERT_NUMBER_IS_HUNDREDS =
            new ResultCode(true, 10015013, "第二次补仓交易的数量必须是整百数");
    public static final ResultCode TOOL_CONVERT_PRICE_IS_EMPTY =
            new ResultCode(true, 10015014, "第二次补仓交易的价格不能为空");


    public static final ResultCode TOOL_REDUCE_NUMBER_IS_EMPTY =
            new ResultCode(true, 10015015, "第二次减仓交易的数量不能为空");
    public static final ResultCode TOOL_REDUCE_NUMBER_IS_HUNDREDS =
            new ResultCode(true, 10015016, "第二次减仓交易的数量必须是整百数");
    public static final ResultCode TOOL_REDUCE_PRICE_IS_EMPTY =
            new ResultCode(true, 10015017, "第二次减仓交易的价格不能为空");

    /**
     * config 变量配置信息：  100 16 （编排）+ 001 (三位随机)
     */
    public static final ResultCode CONFIG_ID_NO_EXIST =
            new ResultCode(true, 10016001, "不存在此配置记录信息");
    public static final ResultCode CONFIG_IS_DEFAULT =
            new ResultCode(true, 10016002, "已经是默认配置，不能删除");
    public static final ResultCode CONFIG_SELECTED_MAX =
            new ResultCode(true, 10016003, "自选股票数量最多10个");
    /**
     * cache 缓存变量配置信息：  100 17 （编排）+ 001 (三位随机)
     */
    public static final ResultCode CACHE_TYPE_IS_EMPTY =
            new ResultCode(true, 10017001, "缓存类型是空");
    public static final ResultCode CACHE_KEY_IS_EMPTY =
            new ResultCode(true, 10017002, "缓存的值必须指定");

    /**
     * 交易规则和条件列表  100 18（编排）+ 001 (三位随机)
     */
    public static final ResultCode RULE_CONDITION_ID_NOT_EXIST =
            new ResultCode(true, 10018001, "交易规则条件关键字不存在,请刷新后重试");
    public static final ResultCode RULE_ID_NOT_EXIST =
            new ResultCode(true, 10018002, "交易规则条件不存在,请刷新后重试");
    public static final ResultCode NO_AUTH =
            new ResultCode(true, 10018003, "没有权限操作");
    /**
     * 持仓信息 100 19（编排）+ 001 (三位随机)
     */
    public static final ResultCode TRADE_MOCK_TYPE_IS_EMPTY =
            new ResultCode(true, 10019001, "未选择交易的类型");
    public static final ResultCode TRADE_MOCK_TYPE_NOT_EXIST =
            new ResultCode(true, 10019002, "交易的类型只支持模拟和真实两种");
    public static final ResultCode TRADE_POSITION_FAIL =
            new ResultCode(true, 10019003, "查询真实持仓信息失败");
    public static final ResultCode TRADE_MONEY_FAIL=
            new ResultCode(true, 10019004, "查询真实持仓金额信息失败");
    public static final ResultCode TRADE_ENTRUST_FAIL=
            new ResultCode(true, 10019005, "查询真实今日委托信息失败");
    public static final ResultCode TRADE_ENTRUST_HISTORY_FAIL=
            new ResultCode(true, 10019006, "查询真实历史委托信息失败");

    public static final ResultCode TRADE_NO_MONEY=
            new ResultCode(true, 10019007, "该用户没有资产记录");
    public static final ResultCode TRADE_MONEY_LESS=
            new ResultCode(true, 10019008, "你的资产不足,无法申请买入");

    public static final ResultCode TRADE_POSITION_NO=
            new ResultCode(true, 10019009, "没有持仓信息，无法卖出");

    public static final ResultCode TRADE_POSITION_NUM_SUPPORT=
            new ResultCode(true, 10019010, "份额不足，请检查目前持仓数量");

    public static final ResultCode TRADE_ENTRUST_ID_EMPTY =
            new ResultCode(true, 10019011, "传入的委托编号id不正确");

    public static final ResultCode TRADE_ENTRUST_STATUS_ERROR =
            new ResultCode(true, 10019012, "委托状态不正确");

    public static final ResultCode TRADE_DEAL_FAIL =
            new ResultCode(true, 10019013, "查询真实今日成交信息失败");
    public static final ResultCode TRADE_DEAL_HISTORY_FAIL =
            new ResultCode(true, 10019014, "查询真实历史成交信息失败");
    public static final ResultCode STOCK_ASYNC_NO_CHANGE =
            new ResultCode(false, 20010007, "股票信息未发生改变");
    public static final ResultCode STOCK_ASYNC_NO_START_DATE =
            new ResultCode(false, 20010008, "同步历史数据未选择开始日期");
    public static final ResultCode STOCK_ASYNC_NO_END_DATE =
            new ResultCode(false, 20010009, "同步历史数据未选择结束日期");


    /**
     * 定时任务列表  100 20（编排）+ 001 (三位随机)
     */
    public static final ResultCode JOB_ID_NOT_EXIST =
            new ResultCode(true, 10020001, "任务编号不正确,请刷新后重试");
    public static final ResultCode JOB_ID_DISABLE =
            new ResultCode(true, 10020002, "任务编号是禁用状态，不能执行");
    public static final ResultCode CRON_NO_VALID =
            new ResultCode(true, 10020003, "Cron 表达式不正确");
    /**
     * 扩展信息  100 21（编排）+ 001 (三位随机)
     */
    public static final ResultCode EXT_ACCOUNT_EXIST =
            new ResultCode(true, 10021001, "客户账号已经存在");
    public static final ResultCode EXT_ID_NOT_EXIST =
            new ResultCode(true, 10021002, "未查询出客户信息");

    private boolean success;
    private int code;
    private String message;

    public ResultCode() {

    }

    public ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


}
