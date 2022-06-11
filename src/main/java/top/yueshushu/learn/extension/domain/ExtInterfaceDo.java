package top.yueshushu.learn.extension.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 使用的功能接口
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ext_interface")
public class ExtInterfaceDo {

    private static final long serialVersionUID = 1L;

    /**
     * id编号自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 接口名称
     */
    @TableField("name")
    private String name;

    /**
     * 接口功能描述
     */
    @TableField("description")
    private String description;

    /**
     * 功能接口文档
     */
    @TableField("doc_url")
    private String docUrl;

    /**
     * 是否删除 1为正常 2为删除
     */
    @TableField("flag")
    private Integer flag;
}
