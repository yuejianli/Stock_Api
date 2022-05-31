package top.yueshushu.learn.entity;

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
 * 股票自选表,是用户自己选择的
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
public class StockSelected implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String stockCode;
    private String stockName;
    private Integer userId;
    private Date createTime;
    private Integer jobId;
    private String notes;
    private Integer status;
    private Integer flag;
}
