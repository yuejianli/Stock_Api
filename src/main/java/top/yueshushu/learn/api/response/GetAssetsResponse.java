package top.yueshushu.learn.api.response;

import lombok.Data;

/**
 * 获取资产的响应
 */
@Data
public class GetAssetsResponse {

    /**
     * 总资产
     */
    private String Zzc;
    /**
     * 可用资金
     */
    private String Kyzj;
    /**
     * 可取资金
     */
    private String Kqzj;
    /**
     * 冻结资金
     */
    private String Djzj;
}
