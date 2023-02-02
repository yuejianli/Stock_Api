package top.yueshushu.learn.enumtype;

/**
 * 股票类型
 *
 * @author Yue Jianli
 * @date 2023-01-30
 */

public enum StockType {
    // CB 可转债
    A(0), Index(1), ETF(2), B(3), CB(4);
    private final int value;

    StockType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
