/*
 Navicat Premium Data Transfer

 Source Server         : 我的腾讯云
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : www.yueshushu.top:3306
 Source Schema         : stock

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 19/10/2022 16:23:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`
(
    `id`          int                                                    NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `code`        varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置值',
    `name`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置名称',
    `code_value`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置对应的值',
    `user_id`     int                                                    NULL DEFAULT NULL COMMENT '对应的用户系统',
    `create_time` timestamp(0)                                           NULL DEFAULT NULL COMMENT '创建时间',
    `flag`        int                                                    NULL DEFAULT NULL COMMENT '是否删除 1为正常 0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '全局性系统配置'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ext_customer
-- ----------------------------
DROP TABLE IF EXISTS `ext_customer`;
CREATE TABLE `ext_customer`
(
    `id`           int                                                     NOT NULL AUTO_INCREMENT COMMENT 'id编号',
    `user_account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '本系统分配的用户账号',
    `name`         varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '用户昵称',
    `wx_id`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应的微信用户的账号信息',
    `birthday`     timestamp(0)                                            NULL DEFAULT NULL COMMENT '生日',
    `sex`          int                                                     NULL DEFAULT NULL COMMENT '性别 1为男 2为女',
    `city`         varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '用户所在城市的编码',
    `flag`         int                                                     NULL DEFAULT NULL COMMENT '是否删除 1为正常 0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ext_customer_job
-- ----------------------------
DROP TABLE IF EXISTS `ext_customer_job`;
CREATE TABLE `ext_customer_job`
(
    `id`               int          NOT NULL AUTO_INCREMENT COMMENT 'id编号',
    `ext_customer_id`  int          NOT NULL COMMENT '关联人id',
    `ext_job_id`       int          NOT NULL COMMENT '关联任务id',
    `ext_interface_id` int          NULL DEFAULT NULL COMMENT '关联的接口id',
    `create_time`      timestamp(0) NULL DEFAULT NULL COMMENT '关联创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 460
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '扩展--用户关联任务匹配表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ext_fasting
-- ----------------------------
DROP TABLE IF EXISTS `ext_fasting`;
CREATE TABLE `ext_fasting`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type`           int                                                     NULL DEFAULT NULL COMMENT '1为具体的年月 2为节气',
    `fasting_month`  int                                                     NOT NULL COMMENT '斋戒月',
    `fasting_day`    int                                                     NULL DEFAULT NULL COMMENT '斋戒日',
    `jie_qi`         varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '节气',
    `fasting_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '斋戒原因，逗号分隔',
    `damage`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '伤害,逗号分隔',
    `notes`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 62
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '斋戒期日历表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ext_interface
-- ----------------------------
DROP TABLE IF EXISTS `ext_interface`;
CREATE TABLE `ext_interface`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT 'id编号',
    `code`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '编号',
    `name`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
    `doc_url`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的文档url',
    `flag`        tinyint                                                 NOT NULL COMMENT '1为正常 0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 24
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '扩展--功能表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ext_job_info
-- ----------------------------
DROP TABLE IF EXISTS `ext_job_info`;
CREATE TABLE `ext_job_info`
(
    `id`                         int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `code`                       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '编码标识，与代码中一致',
    `name`                       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务的名称',
    `description`                varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述的信息',
    `param`                      varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '任务携带的参数',
    `author`                     varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '创建者',
    `update_time`                timestamp(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    `cron`                       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
    `trigger_type`               int                                                     NULL DEFAULT NULL COMMENT '触发的类型 1为手动触发 0为自动触发',
    `trigger_status`             tinyint(1)                                              NULL DEFAULT NULL COMMENT '触发状态 1为启动中 0为禁用',
    `trigger_last_time`          timestamp(0)                                            NULL DEFAULT NULL COMMENT '上次触发的时间',
    `trigger_last_result`        int                                                     NULL DEFAULT NULL COMMENT '上次触发的结果 1为正常0为失败',
    `trigger_last_error_message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次触发的失败信息',
    `trigger_next_time`          timestamp(0)                                            NULL DEFAULT NULL COMMENT '下次触发的时间',
    `flag`                       int                                                     NULL DEFAULT NULL COMMENT '状态 1为正常0为删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_job_info_1` (`code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ext_job_interface
-- ----------------------------
DROP TABLE IF EXISTS `ext_job_interface`;
CREATE TABLE `ext_job_interface`
(
    `id`               int          NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ext_job_id`       int          NOT NULL COMMENT '任务id',
    `ext_interface_id` int          NULL DEFAULT NULL COMMENT '接口功能id',
    `create_time`      timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 46
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '任务关联接口表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for holiday_calendar
-- ----------------------------
DROP TABLE IF EXISTS `holiday_calendar`;
CREATE TABLE `holiday_calendar`
(
    `id`           int  NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `holiday_date` date NULL DEFAULT NULL COMMENT '法定日期,不开盘',
    `curr_year`    int  NOT NULL COMMENT '当前年份',
    `date_type`    int  NULL DEFAULT NULL COMMENT '日期类型 1为交易日 2为周末 3为法定节假日',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 63
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '法定假期表(只写入法定的类型)'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_info
-- ----------------------------
DROP TABLE IF EXISTS `job_info`;
CREATE TABLE `job_info`
(
    `id`                         int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `code`                       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '编码标识，与代码中一致',
    `name`                       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务的名称',
    `description`                varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述的信息',
    `param`                      varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '任务携带的参数',
    `author`                     varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '创建者',
    `update_time`                timestamp(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    `cron`                       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
    `trigger_type`               int                                                     NULL DEFAULT NULL COMMENT '触发的类型 1为手动触发 0为自动触发',
    `trigger_status`             tinyint(1)                                              NULL DEFAULT NULL COMMENT '触发状态 1为启动中 0为禁用',
    `trigger_last_time`          timestamp(0)                                            NULL DEFAULT NULL COMMENT '上次触发的时间',
    `trigger_last_result`        int                                                     NULL DEFAULT NULL COMMENT '上次触发的结果 1为正常0为失败',
    `trigger_last_error_message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次触发的失败信息',
    `trigger_next_time`          timestamp(0)                                            NULL DEFAULT NULL COMMENT '下次触发的时间',
    `flag`                       int                                                     NULL DEFAULT NULL COMMENT '状态 1为正常0为删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_job_info_1` (`code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单的名称',
    `pid`         int                                                     NULL DEFAULT NULL COMMENT '上级菜单的id',
    `show_index`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展示的位置信息',
    `url`         varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单的访问地址',
    `show_type`   int                                                     NULL DEFAULT NULL COMMENT '展示类型 1为web端 2为小程序端',
    `create_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '修改时间',
    `flag`        int                                                     NULL DEFAULT NULL COMMENT '是否删除:1为正常0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 33
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '菜单表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `name`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '角色的名称',
    `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
    `create_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    `flag`        int                                                     NULL DEFAULT NULL COMMENT '是否删除: 1为正常 0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`
(
    `id`          int          NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `role_id`     int          NULL DEFAULT NULL COMMENT '角色id',
    `menu_id`     int          NULL DEFAULT NULL COMMENT '菜单id',
    `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 38
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色权限表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`
(
    `id`          int                                                    NOT NULL AUTO_INCREMENT COMMENT 'id编号自增',
    `code`        varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '股票编号',
    `name`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '股票的名称',
    `exchange`    tinyint(1)                                             NOT NULL COMMENT '股票的标识 0为深圳 1为上海 2为北京',
    `full_code`   varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '股票代码的全称',
    `create_time` timestamp(0)                                           NULL DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `flag`        tinyint(1)                                             NULL DEFAULT NULL COMMENT '是否删除 1为正常 2为删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `stock_code_IDX` (`code`) USING BTREE,
    INDEX `stock_full_code_IDX` (`full_code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 16289
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '股票信息基本表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock_history
-- ----------------------------
DROP TABLE IF EXISTS `stock_history`;
CREATE TABLE `stock_history`
(
    `id`                   int                                                    NOT NULL AUTO_INCREMENT COMMENT 'id自增',
    `code`                 varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '股票的编码',
    `name`                 varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '股票的名称',
    `curr_date`            timestamp(0)                                           NULL DEFAULT NULL COMMENT '当天的日期不包括周六周天',
    `highest_price`        decimal(7, 2)                                          NULL DEFAULT NULL COMMENT '最高价格',
    `lowest_price`         decimal(7, 2)                                          NULL DEFAULT NULL COMMENT '最低价格',
    `closing_price`        decimal(7, 2)                                          NULL DEFAULT NULL COMMENT '收盘价',
    `opening_price`        decimal(7, 2)                                          NULL DEFAULT NULL COMMENT '开盘价',
    `yesClosing_price`     decimal(7, 2)                                          NULL DEFAULT NULL COMMENT '前收盘',
    `amplitude`            decimal(6, 2)                                          NULL DEFAULT NULL COMMENT '涨跌额',
    `amplitude_proportion` decimal(5, 2)                                          NULL DEFAULT NULL COMMENT '涨跌幅',
    `trading_volume`       decimal(18, 0)                                         NULL DEFAULT NULL COMMENT '成交量',
    `trading_value`        decimal(18, 2)                                         NULL DEFAULT NULL COMMENT '成交金额',
    `out_dish`             int                                                    NULL DEFAULT NULL COMMENT '外盘数量',
    `inner_dish`           int                                                    NULL DEFAULT NULL COMMENT '内盘数量',
    `changing_proportion`  decimal(7, 2)                                          NULL DEFAULT NULL COMMENT '换手率',
    `than`                 decimal(7, 2)                                          NULL DEFAULT NULL COMMENT '量比',
    `avg_price`            decimal(7, 2)                                          NULL DEFAULT NULL COMMENT '均价',
    `static_price_ratio`   decimal(7, 3)                                          NULL DEFAULT NULL COMMENT '静态市盈率',
    `dynamic_price_ratio`  decimal(7, 3)                                          NULL DEFAULT NULL COMMENT '动态市盈率',
    `ttm_price_ratio`      decimal(7, 3)                                          NULL DEFAULT NULL COMMENT 'TTM 市盈率',
    `buy_hand`             int                                                    NULL DEFAULT NULL COMMENT '买的 前五手',
    `sell_hand`            int                                                    NULL DEFAULT NULL COMMENT '卖的 前五手',
    `appoint_than`         varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '委比',
    `flag`                 tinyint(1)                                             NULL DEFAULT 1 COMMENT '1为正常 0为删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_stock_history_1` (`code`, `curr_date`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 20596
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '股票的历史交易记录表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock_selected
-- ----------------------------
DROP TABLE IF EXISTS `stock_selected`;
CREATE TABLE `stock_selected`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `stock_code`  varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '股票的编号',
    `stock_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '股票的名称',
    `user_id`     int                                                     NULL DEFAULT NULL COMMENT '用户的id',
    `create_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '创建时间',
    `job_id`      int                                                     NULL DEFAULT NULL COMMENT '定时任务id',
    `code_notes`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '笔记',
    `status`      int                                                     NOT NULL COMMENT '状态 0为禁用 1为启用',
    `flag`        int                                                     NULL DEFAULT NULL COMMENT '1是正常0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 34
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '股票自选表,是用户自己选择的'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock_update_log
-- ----------------------------
DROP TABLE IF EXISTS `stock_update_log`;
CREATE TABLE `stock_update_log`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`        varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '股票编码',
    `name`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '股票名称',
    `update_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    `update_type` int                                                     NULL DEFAULT NULL COMMENT '更新类型 1为新上市 2为名称修改 3退市',
    `exchange`    int                                                     NULL DEFAULT NULL COMMENT '交易所类型',
    `full_code`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '股票全名称',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_stock_update_log_1` (`update_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3183
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '股票修改记录表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_deal
-- ----------------------------
DROP TABLE IF EXISTS `trade_deal`;
CREATE TABLE `trade_deal`
(
    `id`           int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`         varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '股票编号',
    `name`         varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
    `deal_date`    timestamp(0)                                            NULL DEFAULT NULL COMMENT '成交时间',
    `deal_type`    int                                                     NULL DEFAULT NULL COMMENT '成交类型 1为买 2为卖',
    `deal_num`     int                                                     NULL DEFAULT NULL COMMENT '成交数量',
    `deal_price`   decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '成交价格',
    `deal_money`   decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '成交金额',
    `deal_code`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '成交编号',
    `entrust_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '委托编号',
    `entrust_type` int                                                     NULL DEFAULT NULL COMMENT '成交方式 1为手动 0为自动',
    `user_id`      int                                                     NULL DEFAULT NULL COMMENT '关联用户',
    `mock_type`    int                                                     NULL DEFAULT NULL COMMENT '类型 1为虚拟 0为正式',
    `flag`         int                                                     NULL DEFAULT NULL COMMENT '类型 1为正常 0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 263
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '成交表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_entrust
-- ----------------------------
DROP TABLE IF EXISTS `trade_entrust`;
CREATE TABLE `trade_entrust`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`           varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '股票编号',
    `name`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '股票名称',
    `entrust_date`   timestamp(0)                                            NULL DEFAULT NULL COMMENT '交易时间',
    `deal_type`      int                                                     NULL DEFAULT NULL COMMENT '交易类型 1为买  2为卖',
    `entrust_num`    int                                                     NULL DEFAULT NULL COMMENT '交易数量',
    `entrust_price`  decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '交易价格',
    `entrust_status` int                                                     NULL DEFAULT NULL COMMENT '交易的状态 1 进行中 2 成交 3 撤回',
    `entrust_code`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '委托编号',
    `use_money`      decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '可用金额',
    `takeout_money`  decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '可取金额',
    `entrust_money`  decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '委托的交易费用',
    `hand_money`     decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '手续费',
    `total_money`    decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '总的交易费,包括手续费',
    `user_id`        int                                                     NULL DEFAULT NULL COMMENT '关联用户',
    `entrust_type`   int                                                     NULL DEFAULT NULL COMMENT '委托方式 1 手动 0 自动',
    `mock_type`      int                                                     NULL DEFAULT NULL COMMENT '类型 1为虚拟 0为正式',
    `flag`           int                                                     NULL DEFAULT NULL COMMENT '1正常 0 删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `entrust_entrust_date_IDX` (`entrust_date`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 306
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '委托表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_method
-- ----------------------------
DROP TABLE IF EXISTS `trade_method`;
CREATE TABLE `trade_method`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `code`        varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '方法编码,与代码中一致',
    `name`        varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '名称',
    `url`         varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的url网址',
    `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法描述信息',
    `create_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    `flag`        int                                                     NULL DEFAULT NULL COMMENT '是否删除 1为正常0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '交易，包括爬虫所使用的url信息'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_money
-- ----------------------------
DROP TABLE IF EXISTS `trade_money`;
CREATE TABLE `trade_money`
(
    `id`            int            NOT NULL AUTO_INCREMENT COMMENT '主键',
    `total_money`   decimal(12, 4) NULL DEFAULT NULL COMMENT '总金额',
    `use_money`     decimal(12, 4) NULL DEFAULT NULL COMMENT '可用金额',
    `market_money`  decimal(12, 4) NULL DEFAULT NULL COMMENT '市值金额',
    `takeout_money` decimal(12, 4) NULL DEFAULT NULL COMMENT '可取金额',
    `profit_money`  decimal(12, 4) NULL DEFAULT NULL COMMENT '盈亏金额',
    `user_id`       int            NULL DEFAULT NULL COMMENT '对应的用户id',
    `mock_type`     int            NULL DEFAULT NULL COMMENT '虚拟类型 1为虚拟 0为真实',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '资金表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_money_history
-- ----------------------------
DROP TABLE IF EXISTS `trade_money_history`;
CREATE TABLE `trade_money_history`
(
    `id`            int            NOT NULL AUTO_INCREMENT COMMENT '主键',
    `curr_date`     timestamp(0)   NULL DEFAULT NULL COMMENT '当前日期',
    `total_money`   decimal(12, 4) NULL DEFAULT NULL COMMENT '总金额',
    `use_money`     decimal(12, 4) NULL DEFAULT NULL COMMENT '可用金额',
    `market_money`  decimal(12, 4) NULL DEFAULT NULL COMMENT '市值金额',
    `takeout_money` decimal(12, 4) NULL DEFAULT NULL COMMENT '可取金额',
    `profit_money`  decimal(12, 4) NULL DEFAULT NULL COMMENT '盈亏金额',
    `user_id`       int            NULL DEFAULT NULL COMMENT '对应的用户id',
    `mock_type`     int            NULL DEFAULT NULL COMMENT '虚拟类型 1为虚拟 0为真实',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 98
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '资金历史表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_position
-- ----------------------------
DROP TABLE IF EXISTS `trade_position`;
CREATE TABLE `trade_position`
(
    `id`               int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `code`             varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '股票编号',
    `name`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '股票的名称',
    `all_amount`       int                                                     NULL DEFAULT 0 COMMENT '总数量',
    `use_amount`       int                                                     NULL DEFAULT 0 COMMENT '可用数量',
    `avg_price`        decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '成本价',
    `price`            decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '当前价',
    `all_money`        decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '总的市值',
    `float_money`      decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '浮动盈亏',
    `today_money`      decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '今日浮动盈亏',
    `float_proportion` decimal(8, 4)                                           NULL DEFAULT NULL COMMENT '盈亏比例',
    `user_id`          int                                                     NULL DEFAULT NULL COMMENT '员工编号',
    `mock_type`        int                                                     NULL DEFAULT NULL COMMENT '类型,1为虚拟 0为正式',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `position_code_IDX` (`code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 44
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '我的持仓表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_position_history
-- ----------------------------
DROP TABLE IF EXISTS `trade_position_history`;
CREATE TABLE `trade_position_history`
(
    `id`               int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `code`             varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '股票编号',
    `name`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '股票的名称',
    `curr_date`        timestamp(0)                                            NULL DEFAULT NULL COMMENT '当前日期',
    `all_amount`       int                                                     NULL DEFAULT NULL COMMENT '总数量',
    `use_amount`       int                                                     NULL DEFAULT NULL COMMENT '可用数量',
    `avg_price`        decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '成本价',
    `price`            decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '当前价',
    `all_money`        decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '总的市值',
    `float_money`      decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '浮动盈亏',
    `today_money`      decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '今日盈亏金额',
    `float_proportion` decimal(8, 4)                                           NULL DEFAULT NULL COMMENT '盈亏比例',
    `user_id`          int                                                     NULL DEFAULT NULL COMMENT '员工编号',
    `mock_type`        int                                                     NULL DEFAULT NULL COMMENT '类型,1为虚拟 0为正式',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `trade_position_history_code_IDX` (`code`, `curr_date`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 182
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '我的持仓历史记录表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_rule
-- ----------------------------
DROP TABLE IF EXISTS `trade_rule`;
CREATE TABLE `trade_rule`
(
    `id`               int                                                     NOT NULL AUTO_INCREMENT COMMENT 'id主键',
    `name`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则名称',
    `condition_code`   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '规则条件编号',
    `condition_type`   int                                                     NULL DEFAULT NULL COMMENT '规则计算 1为小于 2为大于',
    `rule_value_type`  int                                                     NULL DEFAULT NULL COMMENT '比较类型 1为金额 2为比例',
    `rule_value`       decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '规则对应值',
    `trade_num`        int                                                     NULL DEFAULT NULL COMMENT '交易股票数',
    `trade_value_type` int                                                     NULL DEFAULT NULL COMMENT '交易值类型 1为金额 2为比例',
    `trade_price`      decimal(12, 4)                                          NULL DEFAULT NULL COMMENT '交易差值',
    `rule_type`        int                                                     NULL DEFAULT NULL COMMENT '规则类型 1为买入 2为卖出',
    `status`           int                                                     NULL DEFAULT NULL COMMENT '状态 1为正常 0为禁用',
    `user_id`          int                                                     NULL DEFAULT NULL COMMENT '所属用户',
    `mock_type`        int                                                     NULL DEFAULT NULL COMMENT '1为模拟0为实际',
    `create_time`      timestamp(0)                                            NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`      timestamp(0)                                            NULL DEFAULT NULL COMMENT '修改时间',
    `flag`             int                                                     NULL DEFAULT NULL COMMENT '类型 1为正常 0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '交易规则表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_rule_condition
-- ----------------------------
DROP TABLE IF EXISTS `trade_rule_condition`;
CREATE TABLE `trade_rule_condition`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '条件id',
    `code`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '规则编号',
    `name`        varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '规则名称',
    `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则描述',
    `create_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '更新日期',
    `flag`        int                                                     NULL DEFAULT NULL COMMENT '是否删除 1为正常 0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '交易规则可使用的条件表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_rule_stock
-- ----------------------------
DROP TABLE IF EXISTS `trade_rule_stock`;
CREATE TABLE `trade_rule_stock`
(
    `id`          int                                                    NOT NULL AUTO_INCREMENT COMMENT 'id主键',
    `rule_id`     int                                                    NULL DEFAULT NULL COMMENT '规则id',
    `stock_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应股票的编码',
    `create_time` timestamp(0)                                           NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 44
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '规则股票对应信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_user
-- ----------------------------
DROP TABLE IF EXISTS `trade_user`;
CREATE TABLE `trade_user`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '主键id自增',
    `account`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
    `trade_password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易的密码',
    `salt`           varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '加密盐',
    `user_id`        int                                                     NULL DEFAULT NULL COMMENT '关联的登录用户',
    `cookie`         varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的登录cookie',
    `validate_key`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '登录东方财富时对应的验证key',
    `create_time`    timestamp(0)                                            NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    timestamp(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    `flag`           int                                                     NULL DEFAULT NULL COMMENT '是否删除 1为正常 0为删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '交易用户信息'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`              int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `account`         varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户登录账号',
    `name`            varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '用户的昵称',
    `password`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户的密码',
    `token`           varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工登录后的token',
    `phone`           varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '用户手机',
    `email`           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '用户的邮箱',
    `wx_user_id`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的微信id,企业微信发送消息',
    `create_time`     timestamp(0)                                            NULL DEFAULT NULL COMMENT '用户创建时间',
    `update_time`     timestamp(0)                                            NULL DEFAULT NULL COMMENT '用户修改时间',
    `last_login_time` timestamp(0)                                            NULL DEFAULT NULL COMMENT '最后登录时间',
    `status`          int                                                     NULL DEFAULT NULL COMMENT '是否禁用 1是正常 0为 禁用',
    `flag`            int                                                     NULL DEFAULT NULL COMMENT '是否可用 1是可用 0是删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_account_IDX` (`account`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '登录用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`          int          NOT NULL AUTO_INCREMENT COMMENT '主键自增',
    `user_id`     int          NULL DEFAULT NULL COMMENT '员工id',
    `role_id`     int          NULL DEFAULT NULL COMMENT '角色id',
    `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '添加权限时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '员工角色关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for v_stock_history
-- ----------------------------
DROP VIEW IF EXISTS `v_stock_history`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_stock_history` AS
select `t`.`code`                 AS `股票的编码`,
       `t`.`name`                 AS `股票的名称`,
       `t`.`curr_date`            AS `当天的日期不包括周六周天`,
       `t`.`highest_price`        AS `最高价格`,
       `t`.`lowest_price`         AS `最低价格`,
       `t`.`closing_price`        AS `收盘价`,
       `t`.`opening_price`        AS `开盘价`,
       `t`.`yesClosing_price`     AS `前收盘`,
       `t`.`amplitude`            AS `涨跌额`,
       `t`.`amplitude_proportion` AS `涨跌幅`,
       `t`.`trading_volume`       AS `成交量`,
       `t`.`trading_value`        AS `成交金额`,
       `t`.`out_dish`             AS `外盘数量`,
       `t`.`inner_dish`           AS `内盘数量`,
       `t`.`changing_proportion`  AS `换手率`,
       `t`.`than`                 AS `量比`,
       `t`.`avg_price`            AS `均价`,
       `t`.`static_price_ratio`   AS `静态市盈率`,
       `t`.`dynamic_price_ratio`  AS `动态市盈率`,
       `t`.`ttm_price_ratio`      AS `TTM 市盈率`,
       `t`.`buy_hand`             AS `买的 前五手`,
       `t`.`sell_hand`            AS `卖的 前五手`,
       `t`.`appoint_than`         AS `委比`
from `stock_history` `t`
where (`t`.`curr_date` > (now() - interval 7 day))
order by `t`.`code`, `t`.`curr_date`;

-- ----------------------------
-- View structure for v_trade_money
-- ----------------------------
DROP VIEW IF EXISTS `v_trade_money`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_trade_money` AS
select `u`.`name`          AS `姓名`,
       `m`.`total_money`   AS `总金额`,
       `m`.`use_money`     AS `可用金额`,
       `m`.`market_money`  AS `市值金额`,
       `m`.`takeout_money` AS `可取金额`,
       `m`.`profit_money`  AS `昨日盈亏金额`
from (`trade_money` `m`
         left join `user` `u` on ((`m`.`user_id` = `u`.`id`)))
order by `m`.`user_id`;

-- ----------------------------
-- View structure for v_trade_position
-- ----------------------------
DROP VIEW IF EXISTS `v_trade_position`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_trade_position` AS
select `u`.`name`             AS `姓名`,
       `p`.`code`             AS `股票编码`,
       `p`.`name`             AS `股票名称`,
       `p`.`all_amount`       AS `总数量`,
       `p`.`use_amount`       AS `可用数量`,
       `p`.`avg_price`        AS `持仓成本`,
       `p`.`price`            AS `当前价格`,
       `p`.`all_money`        AS `总的金额`,
       `p`.`float_money`      AS `亏损金额`,
       `p`.`float_proportion` AS `亏损比例`
from (`trade_position` `p`
         left join `user` `u` on ((`p`.`user_id` = `u`.`id`)))
order by `p`.`user_id`;

SET FOREIGN_KEY_CHECKS = 1;
