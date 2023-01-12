package top.yueshushu.learn.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Description 用户实体信息
 * @Author yuejianli
 * @Date 2022/5/20 22:57
 **/
@Data
public class User {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String account;
    private String name;
    private String password;
    private String token;
    private String phone;
    private String email;
    private String wxUserId;
    private String dingUserId;
    private Integer rebootId;
    private Date createTime;
    private Date updateTime;
    private Date lastLoginTime;
    private Integer status;
    private Integer flag;
}
