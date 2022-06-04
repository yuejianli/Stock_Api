package top.yueshushu.learn.enumtype;

import org.springframework.util.Assert;

/**
 * 股票更新类型
 * @author 两个蝴蝶飞
 */
public enum StockUpdateType {
    /**
     * 新上市
     */
    NEW(1,"新上市"),
    /**
     * 修改名称
     */
    CHANGE(2,"修改名称"),
    /**
     * 退市
     */
    DELISTING(3,"退市"),
    ;

    private Integer code;

    private String desc;

    private StockUpdateType(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }
    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
