package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 委托单的类型
 * @author 两个蝴蝶飞
 */
public enum EntrustType {
    HANDLER(1,"手动"),
    AUTO(0,"自动");

    private Integer code;

    private String desc;

    private EntrustType(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }

    /**
     * 获取交易的方法
     * @param code
     * @return
     */
    public static EntrustType getEntrustType(Integer code){
        Assert.notNull(code,"code编号不能为空");
        for(EntrustType configCodeType: EntrustType.values()){
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
