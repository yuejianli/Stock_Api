package top.yueshushu.learn.extension.business;

import top.yueshushu.learn.domain.ext.ExtCustomerDo;

/**
 * 扩展定时任务使用
 *
 * @author yuejianli
 * @date 2022-06-10
 */

public interface ExtJobBusiness {
	/**
	 * 早安时，对某个人发送的指令
	 *
	 * @param extCustomerDo 发送人信息
	 */
	void morning(ExtCustomerDo extCustomerDo);
	
	/**
	 * 晚安时，对某个人发送的晚安指令
	 *
	 * @param extCustomerDo 发送人信息
	 */
	void night(ExtCustomerDo extCustomerDo);
}
