package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 股票版块信息
 * </p>
 *
 * @author 岳建立
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_bk")
public class StockBkDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 版块编码
     */
    @TableField("code")
    private String code;

    /**
     * 版块名称
     */
    @TableField("name")
    private String name;

    /**
     * 版块名称
     */
    @TableField("hot_num")
    private String hotNum;
}
