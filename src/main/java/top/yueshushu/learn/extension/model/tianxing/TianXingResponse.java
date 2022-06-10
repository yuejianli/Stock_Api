package top.yueshushu.learn.extension.model.tianxing;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 天行接口响应信息
 * <p>
 * https://www.tianapi.com/apiview/213
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class TianXingResponse<T> implements Serializable {
	/**
	 * 编码  200
	 */
	private Integer code;
	/**
	 * 消息 msg
	 */
	private String msg;
	/**
	 * 接收信息 newslist
	 */
	private List<T> newslist;
}
