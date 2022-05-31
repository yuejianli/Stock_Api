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
 * 规则股票对应信息表
 * </p>
 *
 * @author 两个蝴蝶飞 自定义的
 * @since 2022-01-26
 */
@Data
public class TradeRuleStock implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer ruleId;
    private String stockCode;
    private Date createTime;
}
