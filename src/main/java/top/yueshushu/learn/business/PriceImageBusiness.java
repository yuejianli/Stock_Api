package top.yueshushu.learn.business;

import java.util.List;

/**
 * 价格分时图保存
 *
 * @author Yue Jianli
 * @date 2023-03-17
 */

public interface PriceImageBusiness {
    /**
     * 保存分时图图片
     *
     * @param codeList  股票编码列表
     * @param asyncPool 是否同步池里面的
     */
    void batchSavePriceImage(List<String> codeList, Boolean asyncPool);

    /**
     * 根据股票全编码进行同步保存分时图图片
     *
     * @param fullCodeList 股票全编码列表
     * @param asyncPool    是否同步池里面的
     */
    void batchSavePriceImageByFullCode(List<String> fullCodeList, Boolean asyncPool);
}
