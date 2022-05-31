package top.yueshushu.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 交易用户信息
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
public class TradeUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    private String account;

    private String password;

    private String salt;
    private Integer userId;
    private String cookie;

    private String validateKey;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer flag;


}
