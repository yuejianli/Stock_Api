package top.yueshushu.learn.extension.model.dto.gaodeweather;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * weather 响应信息
 *
 * @author yuejianli
 * @date 2022-06-07
 */
@Data
public class WeatherResponse implements Serializable {
	private Integer status;
	private Integer count;
	private String info;
	private Integer infocode;
	private List<Lives> lives;
	private List<Forecast> forecasts;
}
