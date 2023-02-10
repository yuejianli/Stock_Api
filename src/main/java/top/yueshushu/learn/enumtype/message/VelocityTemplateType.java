package top.yueshushu.learn.enumtype.message;

import org.springframework.util.Assert;

/**
 * 邮件类型类型
 *
 * @author yuejianli
 * @date 2022-06-10
 */

public enum VelocityTemplateType {
    TEST("test", "测试邮箱"),
    TEN10("ten5", "自选股票十个交易日内涨跌记录"),
    POOL("pool", "股票池信息"),
    ;

    private String code;

    private String desc;

    private VelocityTemplateType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
	
	/**
	 * 获取定时任务信息
	 *
	 * @param code
	 * @return
	 */
	public static VelocityTemplateType getVelocityType(String code) {
		Assert.notNull(code, "code编号不能为空");
		for (VelocityTemplateType exchangeType : VelocityTemplateType.values()) {
			if (exchangeType.code.equalsIgnoreCase(code)) {
				return exchangeType;
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDesc() {
		return desc;
	}
}
