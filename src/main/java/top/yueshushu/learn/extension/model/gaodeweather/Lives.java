package top.yueshushu.learn.extension.model.gaodeweather;

import java.io.Serializable;

import lombok.Data;

/**
 * lives实况天气数据信息
 *
 * @author yuejianli
 * @date 2022-06-07
 */
@Data
public class Lives implements Serializable {
	private String province;
	private String city;
	private String adcode;
	private String weather;
	private String temperature;
	private String winddirection;
	private String windpower;
	private String humidity;
	private String reporttime;
}
