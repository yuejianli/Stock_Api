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
 * 股票信息基本表
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock")
public class StockDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id编号自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票编号
     */
    @TableField("code")
    private String code;

    /**
     * 股票的名称
     */
    @TableField("name")
    private String name;

    /**
     * 股票的标识 0为深圳 1为上海 2为北京
     */
    @TableField("exchange")
    private Integer exchange;

    /**
     * 股票代码的全称
     */
    @TableField("full_code")
    private String fullCode;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 是否删除 1为正常 2为删除
     */
    @TableField("flag")
    private Integer flag;


}
