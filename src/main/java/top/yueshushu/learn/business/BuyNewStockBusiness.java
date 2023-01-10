package top.yueshushu.learn.business;

/**
 * 买入新股
 *
 * @author Yue Jianli
 * @date 2023-01-10
 */

public interface BuyNewStockBusiness {
    /**
     * 买入新股
     *
     * @param userId 用户编号
     * @return 是否申请新股
     */
    boolean applyBuyNewStock(Integer userId);
}
