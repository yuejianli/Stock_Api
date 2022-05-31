package top.yueshushu.learn.mode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 法定假期表(只写入法定的类型)
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
public class HolidayCalendarVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /** 使用@JsonFormat注解格式化日期 */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date holidayDate;
    private Integer currYear;
    private Integer dateType;
}
