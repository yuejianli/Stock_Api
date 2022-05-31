package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 法定假期表(只写入法定的类型)
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("holiday_calendar")
public class HolidayCalendarDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 法定日期,不开盘
     */
    @TableField("holiday_date")
    private Date holidayDate;

    /**
     * 当前年
     */
    @TableField("curr_year")
    private Integer currYear;

    /**
     * 日期类型 1为交易日 2为周末 3为法定节假日
     */
    @TableField("date_type")
    private Integer dateType;


}
