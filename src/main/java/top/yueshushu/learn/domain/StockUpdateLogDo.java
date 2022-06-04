package top.yueshushu.learn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 股票更新历史日志表
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_update_log")
public class StockUpdateLogDo implements Serializable {

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
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新类型 1为新上市 2为名称修改 3为退市
     */
    @TableField("update_type")
    private Integer updateType;
}
