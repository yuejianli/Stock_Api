package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 交易定时任务
 *
 * @author 两个蝴蝶飞
 */
public enum ExtJobInfoType {
    MORNING("morning", "早上7点半"),
    NIGHT("night", "晚上10点20"),
    FASTING("fasting", "每天早上8点和晚上10点可能会发送斋戒信息"),
    ;

    private String code;

    private String desc;

    private ExtJobInfoType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取定时任务信息
     *
     * @param code
     * @return
     */
    public static ExtJobInfoType getJobInfoType(String code) {
        Assert.notNull(code, "code编号不能为空");
        for (ExtJobInfoType exchangeType : ExtJobInfoType.values()) {
            if (exchangeType.code.equalsIgnoreCase(code)) {
                return exchangeType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
