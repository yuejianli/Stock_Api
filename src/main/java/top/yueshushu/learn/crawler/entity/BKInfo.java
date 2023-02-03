package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 版块处理
 *
 * @author yuejianli
 * @date 2023-02-02
 */
@Data
public class BKInfo implements Serializable {
    private String code;
    private String name;
}
