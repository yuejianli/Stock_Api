# 临时指定字符集 防中文乱码
/*!40101 SET NAMES utf8 */;

-- 配置
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (1, 'mock', '虚拟盘', '1', 0, '2021-01-02 00:00:00', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (3, 'mock', '虚拟盘', '1', 1, '2022-10-19 14:22:01', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (4, 'tranPrice', '券商的交易手续费', '2.5', 0, '2022-01-03 11:57:55', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (5, 'selectMaxNum', '最大自选数量', '5', 0, '2021-01-02 00:00:00', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (7, 'buySubPrice', '买入差价', '2.00', 0, '2022-05-23 16:17:45', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (8, 'sellSubPrice', '卖出差价', '3.00', 0, '2022-05-23 16:17:45', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (9, 'buySubPrice', '买入差价', '1', 1, '2022-10-19 14:22:40', 0);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (10, 'sellSubPrice', '卖出差价', '0.2', 1, '2022-05-30 22:35:07', 1);
INSERT INTO `stock`.`config`(`id`, `code`, `name`, `code_value`, `user_id`, `create_time`, `flag`)
VALUES (11, 'selectMaxNum', '最大自选数量', '10', 1, '2022-05-31 20:01:50', 1);

-- 假期
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (32, '2022-01-01', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (33, '2022-01-02', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (34, '2022-01-03', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (35, '2022-01-31', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (36, '2022-02-01', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (37, '2022-02-02', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (38, '2022-02-03', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (39, '2022-02-04', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (40, '2022-02-05', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (41, '2022-02-06', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (42, '2022-04-03', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (43, '2022-04-04', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (44, '2022-04-05', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (45, '2022-04-30', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (46, '2022-05-01', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (47, '2022-05-02', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (48, '2022-05-03', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (49, '2022-05-04', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (50, '2022-06-03', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (51, '2022-06-04', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (52, '2022-06-05', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (53, '2022-09-10', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (54, '2022-09-11', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (55, '2022-09-12', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (56, '2022-10-01', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (57, '2022-10-02', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (58, '2022-10-03', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (59, '2022-10-04', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (60, '2022-10-05', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (61, '2022-10-06', 2022, 3);
INSERT INTO `stock`.`holiday_calendar`(`id`, `holiday_date`, `curr_year`, `date_type`)
VALUES (62, '2022-10-07', 2022, 3);


-- 定时任务
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (1, 'holiday', '假期同步', '每年1月1号执行', NULL, 'system', '2022-10-19 16:06:52', '1 1 0 1 1 ?', 1, 1,
        '2022-06-07 14:42:59', 1, '/ by zero', '2023-01-01 06:01:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (2, 'positionUseAmount', '同步可用数量', '晚上八点半同步', NULL, 'system', NULL, '1 30 20 ? * 1-5', 1, 1,
        '2022-10-18 15:57:49', 1, NULL, '2022-10-18 20:30:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (3, 'yesterdayPrice', '同步上一交易日价格', '早上8点10分同步昨天的价格', NULL, 'system', NULL, '1 10 8 * * ?', 0, 1,
        '2022-10-19 08:10:03', 1, NULL, '2022-10-20 08:10:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (4, 'stockHistory', '股票这一交易日历史同步', '晚上18点10分同步今日交易信息', NULL, 'system', NULL, '1 10 18 * * ?', 1, 1,
        '2022-10-18 15:57:24', 1, NULL, '2022-10-18 18:10:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (5, 'stockPrice', '实时获取股票价格', '每隔30s获取一下股票价格', NULL, 'system', '2022-10-02 22:16:28',
        '1/30 * 9,10,11,13,14 ? * 1-5', 0, 1, '2022-10-19 16:19:31', 1, NULL, '2022-10-19 16:20:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (6, 'tradeIngToRevoke', '自动撤消委托', '晚上8点10分撤消未成交的委托', NULL, 'system', NULL, '1 10 20 ? * 2,3,4,5,6\"', 0, 1,
        '2022-10-18 20:10:02', 1, NULL, '2022-10-19 20:10:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (7, 'tradePositionHistory', '交易持仓信息同步', '同步持仓信息', NULL, 'system', NULL, '1 20 20 ? * 2,3,4,5,6', 1, 1,
        '2022-10-18 15:57:11', 1, NULL, '2022-10-18 20:20:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (8, 'mockDeal', '虚拟成交', '虚拟成交', '1', 'user', '2022-09-27 09:35:58', '2/5 * 9,10,11,13,14,15 ? * 1-5', 0, 0,
        '2022-09-27 09:35:57', 1, 'can\'t parse argument number: ', '2022-09-27 09:36:02', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (9, 'mockEntrust', '虚拟委托', '虚拟委托', '1', 'user', '2022-09-27 09:35:54', '3/5 * 9,10,11,13,14,15 ? * 1-5', 0, 0,
        '2022-09-27 09:35:52', 1, NULL, '2022-09-27 09:35:53', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (10, 'stockUpdate', '股票更新', '股票更新', NULL, 'system', '2022-06-08 11:01:22', '1 20 8 ? * 1-5', 0, 1,
        '2022-10-19 08:20:08', 1, 'can\'t parse argument number: ', '2022-10-20 08:20:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (11, 'stockFiveEmail', '最近五交易日记录发送到邮箱', '最近五交易日记录发送到邮箱', NULL, 'system', '2022-06-08 11:01:22',
        '1 30 20 ? * 1-7', 0, 1, '2022-10-18 20:30:12', 1, 'toIndex = 9', '2022-10-19 20:30:01', 1);
INSERT INTO `stock`.`job_info`(`id`, `code`, `name`, `description`, `param`, `author`, `update_time`, `cron`,
                               `trigger_type`, `trigger_status`, `trigger_last_time`, `trigger_last_result`,
                               `trigger_last_error_message`, `trigger_next_time`, `flag`)
VALUES (12, 'callProfit', '记录每天的盈亏数', '记录每天的盈亏数', NULL, 'system', '2022-10-14 11:01:22', '1 2 15 ? * 1-5', 0, 1,
        '2022-10-19 15:02:03', 1, NULL, '2022-10-20 15:02:01', 1);

-- 菜单

INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (1, '股票配置', 0, '1-0-0', NULL, 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (2, '股票信息', 1, '1-1-0', 'stock', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (3, '股票自选历史记录', 1, '1-2-0', 'stock_history', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (4, '自选股票', 1, '1-3-0', 'stockSelected', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (5, '股票小工具', 0, '2-0-0', NULL, 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (6, '清仓', 5, '2-1-0', 'tool_clear', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (7, '补仓', 5, '2-2-0', 'cover', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (8, '减仓', 5, '2-3-0', 'reduce', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (9, '首页', 0, '0-0-0', 'dashboard', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (10, '系统配置', 0, '3-0-0', '', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (11, '缓存配置', 10, '3-1-0', 'cache', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (12, '参数配置', 10, '3-2-0', 'config', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (13, '年假期同步', 10, '3-3-0', 'holiday', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (14, '交易信息', 0, '4-0-0', '', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (15, '我的持仓', 14, '4-1-0', 'position', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (16, '当日委托', 14, '4-2-0', 'entrust', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (17, '当日成交', 14, '4-3-0', 'deal', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (18, '历史委托', 14, '4-4-0', 'his_entrust', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (19, '历史成交', 14, '4-5-0', 'his_deal', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (20, '模拟交易信息', 0, '5-0-0', '', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (21, '模拟我的持仓', 20, '5-1-0', 'mock_position', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (22, '模拟当日委托', 20, '5-2-0', 'mock_entrust', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (23, '模拟当日成交', 20, '5-3-0', 'mock_deal', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (24, '模拟历史委托', 20, '5-4-0', 'mock_his_entrust', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (25, '模拟历史成交', 20, '5-5-0', 'mock_his_deal', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (26, '交易条件', 27, '4-1-0', 'condition', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (27, '交易规则', 0, '4-0-0', '', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (28, '买入规则', 27, '4-2-0', 'buy_rule', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (29, '卖出规则', 27, '4-3-0', 'sell_rule', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (30, '股票对应规则', 27, '4-4-0', 'stock_rule', 1, '2022-01-02 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (31, '模拟历史持仓信息', 20, '5-6-0', 'mock_his_position', 1, '2022-07-07 00:00:00', NULL, 1);
INSERT INTO `stock`.`menu`(`id`, `name`, `pid`, `show_index`, `url`, `show_type`, `create_time`, `update_time`, `flag`)
VALUES (32, '模拟历史金额信息', 20, '5-7-0', 'mock_hs_mone', 1, '2022-07-07 20:35:31', NULL, 1);
-- 角色

INSERT INTO `stock`.`role`(`id`, `name`, `description`, `create_time`, `update_time`, `flag`)
VALUES (1, '超级管理员', '超级管理员', '2022-01-02 00:00:00', '2022-01-02 00:00:00', 1);

-- 菜单角色权限
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (1, 1, 1, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (2, 1, 2, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (3, 1, 3, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (4, 1, 4, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (5, 1, 5, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (6, 1, 6, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (7, 1, 7, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (8, 1, 8, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (9, 1, 9, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (10, 1, 10, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (11, 1, 11, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (12, 1, 12, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (13, 1, 13, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (14, 1, 14, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (15, 1, 15, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (16, 1, 16, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (17, 1, 17, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (18, 1, 18, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (19, 1, 19, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (20, 1, 20, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (21, 1, 21, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (22, 1, 22, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (23, 1, 23, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (24, 1, 24, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (25, 1, 25, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (26, 1, 26, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (27, 1, 27, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (28, 1, 28, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (29, 1, 29, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (30, 1, 30, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (31, 1, 31, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (32, 1, 32, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (33, 1, 33, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (34, 1, 34, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (35, 1, 35, '2022-01-02 00:00:00');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (36, 1, 36, '2022-07-07 20:35:51');
INSERT INTO `stock`.`role_menu`(`id`, `role_id`, `menu_id`, `create_time`)
VALUES (37, 1, 37, '2022-07-07 20:35:51');

-- 交易方法
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (1, 'get_asserts', '我的资产', 'https://jywg.18.cn/Com/GetAssetsEx?validatekey={0}', '我的资产', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (2, 'submit', '提交挂单', 'https://jywg.18.cn/Trade/SubmitTradeV2?validatekey={0}', '提交挂单', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (3, 'revoke', '撤单', 'https://jywg.18.cn/Trade/RevokeOrders?validatekey={0}', '撤单', '2022-01-02 00:00:00',
        '2022-01-02 00:00:00', 1);
INSERT INTO `stock`.`trade_method`(`id`, `code`, `name`, `url`, `description`, `create_time`, `update_time`, `flag`)
VALUES (4, 'get_stock_list', '我的持仓', 'https://jywg.18.cn/Search/GetStockList?validatekey={0}', '我的持仓',
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

-- 交易用户
INSERT INTO `stock`.`trade_user`(`id`, `account`, `trade_password`, `salt`, `user_id`, `cookie`, `validate_key`,
                                 `create_time`, `update_time`, `flag`)
VALUES (1, '你的东方财富的账号', '你的东方财富加密后的密码', '23', 1,
        'Khmc=%e5%b2%b3%e5%bb%ba%e7%ab%8b; Uid=0ptjWL0Br6Q1LRy5Azy51A%3d%3d; Uuid=a6e616c25388443eb958eee82f0a66d4; Yybdm=5406; mobileimei=',
        'a8a16a60-', '2022-01-02 00:00:00', '2022-01-02 00:00:00', 1);

-- 登录用户
INSERT INTO `stock`.`user`(`id`, `account`, `name`, `password`, `token`, `phone`, `email`, `wx_user_id`, `create_time`,
                           `update_time`, `last_login_time`, `status`, `flag`)
VALUES (1, 'yjl', '岳建立', '11adbb322b500449',
        'eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjU5MjIwMTUsInRpbWVzdGFtcCI6MTY2NTkxODQxNTgzNn0.2QAAc1Iwbfgy31N_bVyHI1ONmsEifo4uhtfUwDzECfI',
        '15734078927', '1290513799@qq.com', 'YueJianLi', '2022-01-02 00:00:00', '2022-01-02 00:00:00',
        '2022-10-16 19:06:56', 1, 1);

-- 用户配置角色
INSERT INTO `stock`.`user_role`(`id`, `user_id`, `role_id`, `create_time`)
VALUES (1, 1, 1, '2022-01-02 00:00:00');



