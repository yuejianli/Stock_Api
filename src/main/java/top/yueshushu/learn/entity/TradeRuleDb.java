package top.yueshushu.learn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 交易规则表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-26
 */
@Data
public class TradeRuleDb implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer codeType;
    private Integer buyNum;
    private String buyParam;
    private String buyStockCode;
    private Date createTime;
    private Date updateTime;
    private Integer userId;
    private Integer mockType;
    private Integer flag;
}
