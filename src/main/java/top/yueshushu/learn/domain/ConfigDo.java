package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yueshushu.learn.common.Const;

/**
 * <p>
 * 全局性系统配置
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("config")
public class ConfigDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置值
     */
    @TableField("code")
    private String code;

    /**
     * 配置名称
     */
    @TableField("name")
    private String name;

    /**
     * 配置对应的值
     */
    @TableField("code_value")
    private String codeValue;

    /**
     * 对应的用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 是否删除 1为正常 0为删除
     */
    @TableField("flag")
    private Integer flag;


    public boolean isUserConfig(){
        if(userId==null|| Const.DEFAULT_NO.equals(userId)){
            return false;
        }
        return true;
    }
}
