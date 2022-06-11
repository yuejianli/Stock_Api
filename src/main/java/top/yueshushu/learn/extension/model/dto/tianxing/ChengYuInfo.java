package top.yueshushu.learn.extension.model.dto.tianxing;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 成语使用
 * @Author yuejianli
 * @Date 2022/6/5 7:33
 **/
@Data
public class ChengYuInfo implements Serializable {
    /**
     * 问题
     */
    private String question;
    /**
     * 参考答案
     */
    private String answer;
    /**
     * 拼音
     */
    private String pinyin;
    /**
     * 来源
     */
    private String source;
}
