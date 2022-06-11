package top.yueshushu.learn.extension.entity;

import lombok.Data;

/**
 * 使用的功能接口
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class ExtInterface {

    private static final long serialVersionUID = 1L;
    private Integer id;

    private String code;

    private String name;

    private String description;

    private String docUrl;

    private Integer flag;
}
