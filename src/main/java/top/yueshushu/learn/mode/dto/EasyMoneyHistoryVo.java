package top.yueshushu.learn.mode.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description 东方财富交易所使用的类信息
 * @Author yuejianli
 * @Date 2022/6/4 13:13
 **/
public class EasyMoneyHistoryVo {
    public static class StockResultVo {

        private StockResultDataVo data;

        public StockResultDataVo getData() {
            return data;
        }

        public void setData(StockResultDataVo data) {
            this.data = data;
        }
    }

    public static class StockResultDataVo {

        private List<StockResultDiffVo> diff;

        public List<StockResultDiffVo> getDiff() {
            return diff;
        }

        public void setDiff(List<StockResultDiffVo> diff) {
            this.diff = diff;
        }

    }
    @Data
    public static class StockResultDiffVo {

        private int f1;
        private int f2;
        private int f3;
        private int f4;
        private long f5;
        private double f6;
        private int f7;
        private int f8;
        private int f9;
        private int f10;
        private int f11;
        private String f12;
        private int f13;
        private String f14;
        private int f15;
        private int f16;
        private int f17;
        private int f18;
        private int f20;
        private int f21;
        private int f22;
        private int f23;
        private int f24;
        private int f25;
        private double f62;
    }
}
