package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 委托状态类型
 * @author 两个蝴蝶飞
 */
public enum EntrustStatusType {
    ING(1,"委托中"),
    SUCCESS(2,"成交"),
    REVOKE(3,"撤单");

    private Integer code;

    private String desc;

    private EntrustStatusType(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }

    /**
     * 获取交易的方法
     * @param code
     * @return
     */
    public static EntrustStatusType getEntrustStatusType(Integer code){
        Assert.notNull(code,"code编号不能为空");
        for(EntrustStatusType configCodeType: EntrustStatusType.values()){
            if(configCodeType.code.equals(code)){
                return configCodeType;
            }
        }
        return null;
    }
    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
