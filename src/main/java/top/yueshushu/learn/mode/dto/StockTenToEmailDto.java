package top.yueshushu.learn.mode.dto;

import lombok.Data;
import top.yueshushu.learn.mode.vo.ten10stat.StockTen10Vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 最近十天的记录发送到用户邮箱的信息
 * @Author yuejianli
 * @Date 2022/6/5 11:13
 **/
@Data
public class StockTenToEmailDto implements Serializable {
    /**
     * 账号
     */
    private String account;
    /**
     * 名称
     */
    private String name;
    /**
     * 日期信息
     */
    private List<String> currDateList;
    /**
     * 最近10天的数据
     */
    private List<StockTen10Vo> dataList;
    /**
     * 分隔符
     */
    private String line;
}
