package top.yueshushu.learn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 股票信息基本表
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private Integer exchange;
    private String fullCode;
    private Integer canUse;
    private Date createTime;
    private String createUser;
    private Integer flag;
}
