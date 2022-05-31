package top.yueshushu.learn.mode.vo.charinfo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:LineSeriesVo
 * @Description 拆线图具体的信息
 * @Author 岳建立
 * @Date 2021/11/27 14:05
 * @Version 1.0
 **/
@Data
public class LineSeriesVo implements Serializable {
    private String name;
    private String type="line";
    private String stack="Total";
    private List<Double> data=new ArrayList<>();
}
