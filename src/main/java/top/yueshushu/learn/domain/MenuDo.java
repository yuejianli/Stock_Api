package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("menu")
public class MenuDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单的名称
     */
    @TableField("name")
    private String name;

    /**
     * 上级菜单的id
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 展示的位置信息
     */
    @TableField("show_index")
    private String showIndex;

    /**
     * 菜单的访问地址
     */
    @TableField("url")
    private String url;

    /**
     * 展示类型 1为web端 2为小程序端
     */
    @TableField("show_type")
    private Integer showType;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 是否删除:1为正常0为删除
     */
    @TableField("flag")
    private Integer flag;


}
