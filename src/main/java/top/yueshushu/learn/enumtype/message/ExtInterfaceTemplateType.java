package top.yueshushu.learn.enumtype.message;

import org.springframework.util.Assert;

/**
 * 扩展接口功能信息
 *
 * @author yuejianli
 * @date 2022-06-10
 */

public enum ExtInterfaceTemplateType {
	WEATHER("weather", "获取天气信息"),
	TRANSLATE("translate", "获取每日一句"),
	POEM("poem", "获取一句诗词"),
	COUPLETS("couplets", "获取对联信息"),
	CLASSICAL("classical", "获取古典诗句"),
	DIALOGUE("dialogue", "获取经典对话"),
	CAIHONGPI("caihongpi", "获取彩虹屁"),
	BAIKETIKU("baiketiku", "获取百科题库信息"),
	ZAOAN("zaoan", "获取早安的信息"),
	WANAN("wanan", "获取晚安的信息"),
	TENWHY("tenwhy", "获取十万个为什么"),
	ZIMI("zimi", "获取字迷信息"),
	PROVERB("proverb", "获取文化谚语"),
	
	;
	
	private String code;
	
	private String desc;
	
	private ExtInterfaceTemplateType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	/**
	 * @param code
	 * @return
	 */
	public static ExtInterfaceTemplateType getInterfaceType(String code) {
		Assert.notNull(code, "code编号不能为空");
		for (ExtInterfaceTemplateType exchangeType : ExtInterfaceTemplateType.values()) {
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
