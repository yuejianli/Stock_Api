package top.yueshushu.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yueshushu.learn.common.Const;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 全局性系统配置
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private String codeValue;
    private Integer userId;
    private Date createTime;
    private Integer flag;
}
