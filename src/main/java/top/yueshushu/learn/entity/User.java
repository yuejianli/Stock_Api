package top.yueshushu.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户登录账号
     */
    @TableField("account")
    private String account;

    /**
     * 用户的昵称
     */
    @TableField("name")
    private String name;

    /**
     * 用户的密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户登录的token
     */
    @TableField("token")
    private String token;


    /**
     * 用户手机
     */
    @TableField("phone")
    private String phone;

    /**
     * 用户的邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 用户创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 用户修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 是否禁用 1是正常 0为 禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否可用 1是可用 0是删除
     */
    @TableField("flag")
    private Integer flag;
}
