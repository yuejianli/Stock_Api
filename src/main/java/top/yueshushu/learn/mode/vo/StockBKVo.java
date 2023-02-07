package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 版块展示 Vo
 *
 * @author yuejianli
 * @date 2023-02-07
 */
@Data
public class StockBKVo implements Serializable {
    private String code;
    private String name;
}
