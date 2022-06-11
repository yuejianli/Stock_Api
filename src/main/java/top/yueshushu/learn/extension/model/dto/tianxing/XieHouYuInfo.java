package top.yueshushu.learn.extension.model.dto.tianxing;

import lombok.Data;

import java.io.Serializable;

/**
 * 歇后语信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class XieHouYuInfo implements Serializable {
    /**
     * 前半部分
     */
    private String quest;
    /**
     * 后半部分
     */
    private String result;
}
