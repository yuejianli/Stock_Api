package top.yueshushu.learn.extension.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 斋戒日期
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ext_fasting")
public class ExtFastingDo {

	private static final long serialVersionUID = 1L;

	/**
	 * id编号自增
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 1为具体的年月 2为节气
	 */
	@TableField("type")
	private Integer type;

	/**
	 * 农历月
	 */
	@TableField("fasting_month")
	private Integer fastingMonth;

	/**
	 * 农历天
	 */
	@TableField("fasting_day")
	private Integer fastingDay;

	/**
	 * 节气的名称
	 */
	@TableField("jie_qi")
	private String jieQi;

	/**
	 * 原因 ,号分隔
	 */
	@TableField("fasting_reason")
	private String fastingReason;

	/**
	 * 后果  ,号分隔
	 */
	@TableField("damage")
	private String damage;

	/**
	 * 备注
	 */
	@TableField("notes")
	private String notes;
}
