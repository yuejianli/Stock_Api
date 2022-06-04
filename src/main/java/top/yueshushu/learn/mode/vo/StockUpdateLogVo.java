package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 岳建立 自定义的
 * @since 2022-06-04
 */
@Data
public class StockUpdateLogVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private Integer exchange;
    private String fullCode;
    private Date updateTime;
    private Integer updateType;
}
