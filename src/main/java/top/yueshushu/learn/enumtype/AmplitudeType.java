package top.yueshushu.learn.enumtype;

/**
 * @Description 涨跌类型
 * @Author yuejianli
 * @Date 2022/6/5 7:47
 **/
public enum AmplitudeType {
    /**
     * 正常
     */
    ROSE(1, "涨"),
    /**
     * 亏损
     */
    WANE(-1, "跌"),
    /**
     * 平
     */
    FLAT(0, "平"),
    /**
     * 未同步
     */
    NODATA(2, "未同步");


    private Integer code;

    private String desc;

    private AmplitudeType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
