package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 追加用户时使用
 *
 * @author yuejianli
 * @date 2022-11-14
 */
@Data
public class AdminOperateUserRequestVo implements Serializable {
    private Integer id;
    private String account;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String dingUserId;
    private String wxUserId;
    private Integer rebootId = 1;
    private Integer roleId;
    private BigDecimal totalMoney = new BigDecimal(200000);
}
