package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 虚拟盘的类型
 * @author 两个蝴蝶飞
 */
public enum MockType {
    MOCK(1,"虚拟盘"),
    REAL(0,"真实盘");

    private Integer code;

    private String desc;

    private MockType(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }

    /**
     * 获取交易的方法
     * @param code
     * @return
     */
    public static MockType getMockType(Integer code){
        Assert.notNull(code,"code编号不能为空");
        for(MockType configCodeType: MockType.values()){
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
