package top.yueshushu.learn.entity.ext;

import lombok.Data;

/**
 * 关联使用的客户信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Data
public class ExtCustomer {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * id编号自增
	 */
	private Integer id;
	
	/**
	 * 用户的账号
	 */
	private String userAccount;
	
	/**
	 * 用户的昵称
	 */
	private String name;
	
	/**
	 * 用户的微信id号
	 */
	private String userId;
	
	/**
	 * 性别  1为男  0为 女
	 */
	private Integer sex;
	
	/**
	 * 用户所在城市的编码
	 */
	private String city;
	
	/**
	 * 是否删除 1为正常 2为删除
	 */
	private Integer flag;
}
