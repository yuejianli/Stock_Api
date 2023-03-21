package top.yueshushu.learn.mode.ro;

import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * 查询用户使用
 *
 * @author yuejianli
 * @date 2023-03-20
 */
@Data
public class QueryUserRo extends PageRo implements Serializable {
    private String keyword;
    private Integer status;
    private Integer id;
    private String account;
    private Integer flag = 1;
}
