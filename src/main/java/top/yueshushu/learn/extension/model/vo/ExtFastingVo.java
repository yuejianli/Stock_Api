package top.yueshushu.learn.extension.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 斋戒日期
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class ExtFastingVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id编号自增
     */
    private Integer id;

    /**
     * 1为具体的年月 2为节气
     */
    private Integer type;


    /**
     * 农历月
     */
    private Integer fastingMonth;

    /**
     * 农历月名称
     */
    private String fastingMonthName;
    /**
     * 农历天
     */
    private Integer fastingDay;

    /**
     * 农历天名称
     */
    private String fastingDayName;
    /**
     * 节气的名称
     */
    private String jieQi;

    /**
     * 原因 ,号分隔
     */
    private String fastingReason;

    /**
     * 原因 ,号分隔
     */
    private List<String> fastingReasonList;

    /**
     * 后果  ,号分隔
     */
    private String damage;

    /**
     * 后果  ,号分隔
     */
    private List<String> damageList;

    /**
     * 备注
     */
    private String notes;
}
