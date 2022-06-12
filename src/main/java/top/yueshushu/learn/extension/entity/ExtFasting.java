package top.yueshushu.learn.extension.entity;

import lombok.Data;

/**
 * 斋戒日期
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class ExtFasting {

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
     * 农历天
     */
    private Integer fastingDay;

    /**
     * 节气的名称
     */
    private String jieQi;

    /**
     * 原因 ,号分隔
     */
    private String fastingReason;

    /**
     * 后果  ,号分隔
     */
    private String damage;

    /**
     * 备注
     */
    private String notes;
}
