package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 查询的类型
 * @author 两个蝴蝶飞
 */
public enum SelectedType {
    POSITION(1,"持仓"),
    SELECTED(2,"持仓和自选");

    private Integer code;

    private String desc;

    private SelectedType(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }

    /**
     * 获取交易的方法
     * @param code
     * @return
     */
    public static SelectedType getSelectedType(Integer code){
        Assert.notNull(code,"code编号不能为空");
        for(SelectedType configCodeType: SelectedType.values()){
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
