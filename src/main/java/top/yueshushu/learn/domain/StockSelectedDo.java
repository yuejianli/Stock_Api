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
 * 股票自选表,是用户自己选择的
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_selected")
public class StockSelectedDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票的编号
     */
    @TableField("stock_code")
    private String stockCode;
    /**
     * 股票的名称
     */
    @TableField("stock_name")
    private String stockName;
    /**
     * 用户的id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 定时任务的id
     */
    @TableField("job_id")
    private Integer jobId;

    /**
     * 笔记
     */
    @TableField("code_notes")
    private String notes;

    /**
     * 是否启用，禁用  1为启用,0为禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否删除 1为正常 2为删除
     */
    @TableField("flag")
    private Integer flag;


}
