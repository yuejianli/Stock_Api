package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 交易用户信息
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("trade_user")
public class TradeUserDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     */
    @TableField("account")
    private String account;

    /**
     * 交易的密码
     */
    @TableField("trade_password")
    private String password;

    /**
     * 加密盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 关联的登录用户
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 对应的登录cookie
     */
    @TableField("cookie")
    private String cookie;

    /**
     * 登录东方财富时对应的验证key
     */
    @TableField("validate_key")
    private String validateKey;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除 1为正常 0为删除
     */
    @TableField("flag")
    private Integer flag;


}
