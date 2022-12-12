package top.yueshushu.learn.mode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    /**
     * 使用@JsonFormat注解格式化日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
    private Integer updateType;
}
