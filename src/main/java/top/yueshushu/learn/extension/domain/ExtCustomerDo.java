package top.yueshushu.learn.extension.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 关联使用的客户信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ext_customer")
public class ExtCustomerDo {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * id编号自增
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	/**
	 * 用户的账号
	 */
	@TableField("user_account")
	private String userAccount;
	
	/**
	 * 用户的昵称
	 */
	@TableField("name")
	private String name;

    /**
     * 用户的微信id号
     */
    @TableField("wx_id")
    private String wxId;

    /**
     * 性别  1为男  0为 女
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 生日
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 用户所在城市的编码
     */
    @TableField("city")
    private String city;

    /**
     * 是否删除 1为正常 2为删除
     */
	@TableField("flag")
	private Integer flag;
}
