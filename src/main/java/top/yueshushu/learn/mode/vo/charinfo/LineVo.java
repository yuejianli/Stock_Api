package top.yueshushu.learn.mode.vo.charinfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:LineVo
 * @Description 拆线图对象信息
 * @Author 岳建立
 * @Date 2021/11/27 14:04
 * @Version 1.0
 **/
@Data
public class LineVo implements Serializable {
    /**
     * 展示的legend
     */
    private List<String> legend;
    /**
     * 横坐标的值
     */
    private List<String> xaxisData;
    /**
     * 处理信息
     */
    private List<LineSeriesVo> series;
}
