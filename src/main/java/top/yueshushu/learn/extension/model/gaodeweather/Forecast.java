package top.yueshushu.learn.extension.model.gaodeweather;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * lives预报情况
 *
 * @author yuejianli
 * @date 2022-06-07
 */
@Data
public class Forecast implements Serializable {
	private String province;
	private String city;
	private String adcode;
	private String reporttime;
	private List<ForecastCasts> casts;
}
