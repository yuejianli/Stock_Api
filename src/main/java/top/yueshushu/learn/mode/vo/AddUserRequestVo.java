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
public class AddUserRequestVo implements Serializable {
    private String account;
    private String name;
    private String email;
    private String wxUserId;
    private BigDecimal totalMoney = new BigDecimal(200000);
}
