-- 插入 config 配置表
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (1, 'mock', '虚拟盘', '1', 0, '2021-01-02 00:00:00', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (4, 'tranPrice', '券商的交易手续费', '2.5', 0, '2022-01-03 11:57:55', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (5, 'selectMaxNum', '最大自选数量', '5', 0, '2021-01-02 00:00:00', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (7, 'buySubPrice', '买入差价', '2.00', 0, '2022-05-23 16:17:45', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (8, 'sellSubPrice', '卖出差价', '3.00', 0, '2022-05-23 16:17:45', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (15, 'buyNewStock', '申购新股', '0', 0, '2023-01-10 17:13:59', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (16, 'autoLogin', '自动登录', '0', 0, '2023-01-10 18:26:23', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (19, 'todayBuyNum', '今天买入交易次数', '3', 0, '2023-02-01 10:19:29', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (20, 'todaySellNum', '今天卖出交易次数', '3', 0, '2023-02-01 10:19:56', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (21, 'dbEnable', '是否启用打板交易', '0', 0, '2023-02-09 13:41:59', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (22, 'dbStockType', '打板的股票类型', '0', 0, '2023-02-09 13:47:46', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (23, 'dbBuyNum', '打板买入次数', '0', 0, '2023-02-09 14:42:30', 1);



INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (63, '2023-01-01', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (64, '2023-01-02', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (65, '2023-01-21', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (66, '2023-01-22', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (67, '2023-01-23', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (68, '2023-01-24', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (69, '2023-01-25', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (70, '2023-01-26', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (71, '2023-01-27', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (72, '2023-04-05', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (73, '2023-04-29', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (74, '2023-04-30', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (75, '2023-05-01', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (76, '2023-05-02', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (77, '2023-05-03', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (78, '2023-06-22', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (79, '2023-06-23', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (80, '2023-06-24', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (81, '2023-09-29', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (82, '2023-09-30', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (83, '2023-10-01', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (84, '2023-10-02', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (85, '2023-10-03', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (86, '2023-10-04', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (87, '2023-10-05', 2023, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (88, '2023-10-06', 2023, 3);



INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (1, 'holiday', '假期同步', '每年1月1号执行', NULL, 'system', '2023-03-06 20:04:42', '1 1 0 1 1 ?', 1, 1,
        '2023-01-03 08:54:22', 1, '/ by zero', '2024-01-01 00:01:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (2, 'positionUseAmount', '同步可用数量', '晚上八点20 同步', NULL, 'system', NULL, '1 20 20 ? * 1-5', 0, 1,
        '2023-03-08 20:20:01', 1, NULL, '2023-03-09 20:20:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (3, 'yesterdayPrice', '同步上一交易日价格', '早上8点10分同步昨天的价格', NULL, 'system', NULL, '1 10 8 ? * 1-5', 0, 1,
        '2023-03-09 08:10:01', 1, NULL, '2023-03-12 08:10:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (4, 'stockHistory', '股票这一交易日历史同步', '晚上18点10分同步今日交易信息', NULL, 'system', NULL, '1 10 18 ? * 1-5', 0, 1,
        '2023-03-08 18:10:01', 1, NULL, '2023-03-09 18:10:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (5, 'stockPrice', '实时获取股票价格', '每隔5s获取一下股票价格', NULL, 'system', '2022-10-13 00:16:28',
        '1/5 * 9,10,11,13,14 ? * 1-5', 0, 1, '2023-03-09 14:23:01', 1, NULL, '2023-03-09 14:23:06', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (6, 'tradeIngToRevoke', '自动撤消委托', '晚上8点10分撤消未成交的委托', NULL, 'system', NULL, '1 10 20 ? * 1-5', 0, 1,
        '2023-03-08 20:10:01', 1, NULL, '2023-03-09 20:10:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (7, 'tradePositionHistory', '交易持仓信息同步', '同步持仓信息', NULL, 'system', NULL, '1 40 20 ? * 1-5', 0, 1,
        '2023-03-08 20:40:01', 1,
        'nested exception is org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 2',
        '2023-03-09 20:40:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (8, 'mockDeal', '虚拟成交', '虚拟成交', '1,8', 'user', '2022-09-27 15:35:58', '15/20 * 9,10,11,13,14 ? * 1-5', 0, 1,
        '2023-03-09 14:22:55', 1, 'can\'t parse argument number: ', '2023-03-09 14:23:15', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (9, 'mockEntrust', '虚拟委托', '虚拟委托', '1,8', 'user', '2022-09-27 13:35:54', '2/20 * 9,10,11,13,14 ? * 1-5', 0, 1,
        '2023-03-09 14:23:02', 1, '格式化的源 bigDecimal1 不能为空', '2023-03-09 14:23:22', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (10, 'stockUpdate', '股票更新', '股票更新', NULL, 'system', '2022-06-08 11:01:22', '1 10 9 ? * 1-5', 0, 1,
        '2023-03-09 09:10:04', 1,
        'Duplicate key StockDo(id=16289, code=301359, name=东南电子, exchange=0, fullCode=sz301359, createTime=Wed Oct 19 08:20:05 CST 2022, createUser=job, flag=1)',
        '2023-03-12 09:10:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (11, 'stockFiveEmail', '最近十交易日记录发送到邮箱', '最近十交易日记录发送到邮箱', NULL, 'system', '2022-06-08 11:01:22',
        '1 30 20 ? * 1-7', 0, 1, '2023-03-08 20:30:09', 1, '格式化的源 BigDecimal不能为空', '2023-03-09 20:30:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (12, 'callProfit', '记录每天的盈亏数', '记录每天的盈亏数', NULL, 'system', '2022-10-14 11:01:22', '1 2 15 ? * 1-5', 0, 1,
        '2023-03-08 15:02:02', 1, '格式化的源 bigDecimal2 不能为空', '2023-03-09 15:02:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (13, 'bigDeal', '大宗交易信息同步', '大宗交易信息同步', '1', 'system', '2022-11-29 18:59:56', '1 20 18 ? * 1-5', 0, 0,
        '2022-11-29 19:00:39', 1, NULL, '2022-11-29 19:00:49', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (14, 'syncEasyMoney', '同步东财数据', '同步东财数据', '', 'system', '2023-01-10 18:59:56', '1 3 15 ? * 1-5', 0, 1,
        '2023-03-08 15:03:06', 1, NULL, '2023-03-09 15:03:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (15, 'buyNewStock', '新股申购', '新股申购', '', 'system', '2023-01-10 18:59:56', '0 1 10,14 ? * 1-5', 0, 1,
        '2023-03-09 14:01:00', 1, NULL, '2023-03-12 10:01:00', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (16, 'autoLogin', '自动登录', '自动登录', '', 'system', '2023-01-10 18:59:56', '0 10,30,50 9-15 ? * 1-5', 0, 1,
        '2023-03-09 14:10:00', 1, NULL, '2023-03-09 14:30:00', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (17, 'stockBk', '股票版块同步', '股票版块同步', '', 'system', '2023-01-10 18:59:56', '1 10 15 ? * 1-5', 0, 1,
        '2023-03-08 15:10:02', 1, NULL, '2023-03-09 15:10:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (18, 'stockPriceSave', '股票价格每分钟保存', '每隔 1min保存一下股票价格', '600733', 'system', '2022-10-12 08:16:28',
        '10 0/1 9,10,11,13,14 ? * 1-5', 0, 1, '2023-03-09 14:22:10', 1, NULL, '2023-03-09 14:23:10', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (19, 'dbStockTrade', '股票打版买入', '每隔 10s 试图打版买入股票', '', 'system', '2023-03-06 19:54:51',
        '0/10 * 9,10,11,13,14 ? * 1-5', 0, 1, '2023-03-09 14:23:00', 1, NULL, '2023-03-09 14:23:10', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (20, 'stockPoolQs', '股票池强势股处理', '每隔10分钟获取股票强势池信息', NULL, 'system', '2023-02-10 14:02:19',
        '1 5,15,25,35,45,55 9,10,11,13,14 ? * 1-5', 0, 1, '2023-03-09 14:15:03', 1, NULL, '2023-03-09 14:25:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (21, 'stockPool', '股票池处理', '晚上9点40同步股票池信息', NULL, 'system', '2023-02-10 14:02:19', '1 40 21 ? * 1-5', 0, 1,
        '2023-03-08 21:40:04', 1, NULL, '2023-03-09 21:40:01', 1);


INSERT INTO `stock`.`reboot`(`id`, `code`, `description`, `webhook`, `param`, `status`, `create_time`, `update_time`)
VALUES (1, 'learn', '钉钉群--简单的',
        'https://oapi.dingtalk.com/robot/send?access_token=817edae9c662dc38bb5fcb4231f74b9e1eb9f33d17f1e12b25903980f82b7c9d',
        '友情提醒:', 1, '2023-01-11 17:21:11', '2023-01-11 17:21:13');


INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (1, 'get_asserts', '我的资产', 'https://jywg.18.cn/Com/queryAssetAndPositionV1?validatekey={0}', '我的资产',
        '2022-01-02 00:00:00', '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (2, 'submit', '提交挂单', 'https://jywg.18.cn/Trade/SubmitTradeV2?validatekey={0}', '提交挂单', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (3, 'revoke', '撤单', 'https://jywg.18.cn/Trade/RevokeOrders?validatekey={0}', '撤单', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (4, 'get_stock_list', '我的持仓', 'https://jywg.18.cn/Com/queryAssetAndPositionV1?validatekey={0}', '我的持仓',
        '2022-01-02 00:00:00', '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (5, 'get_orders_data', '当日委托', 'https://jywg.18.cn/Search/GetOrdersData?validatekey={0}', '当日委托',
        '2022-01-02 00:00:00', '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (6, 'get_deal_data', '当日成交', 'https://jywg.18.cn/Search/GetDealData?validatekey={0}', '当日成交',
        '2022-01-02 00:00:00', '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (7, 'authentication', '登录', 'https://jywg.18.cn/Login/Authentication', '登录', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (8, 'yzm', '登录验证码', 'https://jywg.18.cn/Login/YZM?randNum={0}&_ra={1}', '登录验证码', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (9, 'authentication_check', '登录验证', 'https://jywg.18.cn/Trade/Buy', '登录验证', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (10, 'get_his_deal_data', '历史成交', 'https://jywg.18.cn/Search/GetHisDealData?validatekey={0}', '历史成交',
        '2022-01-02 00:00:00', '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (11, 'get_his_orders_data', '历史委托', 'https://jywg.18.cn/Search/GetHisOrdersData?validatekey={0}', '历史委托',
        '2022-01-02 00:00:00', '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (12, 'get_can_buy_new_stock_list_v3', '查询可申购新股列表',
        'https://jywg.18.cn/Trade/GetCanBuyNewStockListV3?validatekey={0}', '查询可申购新股列表', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (13, 'get_convertible_bond_list_v2', '查询可申购新债列表',
        'https://jywg.18.cn/Trade/GetConvertibleBondListV2?validatekey={0}', '查询可申购新债列表', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (14, 'submit_bat_trade_v2', '批量申购', 'https://jywg.18.cn/Trade/SubmitBatTradeV2?validatekey={0}', '批量申购',
        '2022-01-02 00:00:00', '2022-01-02 00:00:00', 1);


INSERT INTO `stock`.`trade_user`(`id`, `account`, `trade_password`, `salt`, `user_id`, `cookie`, `validate_key`,
                                 `create_time`,
                                 `update_time`, `flag`)
VALUES (1, '加密后账号', '加密后密码', '', 1, 'cookie', 'validate_key', '2022-01-02 00:00:00', '2023-03-06 09:30:01', 1);

INSERT INTO `stock`.`user`(`id`, `account`, `name`, `password`, `token`, `phone`, `email`, `wx_user_id`, `ding_user_id`,
                           `reboot_id`,
                           `create_time`, `update_time`, `last_login_time`, `status`, `flag`)
VALUES (1, 'yjl', '岳建立', '密码', 'token值', '', '1290513799@qq.com', '', '15734078927', 1, '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', '2023-02-24 14:04:07', 1, 1);

