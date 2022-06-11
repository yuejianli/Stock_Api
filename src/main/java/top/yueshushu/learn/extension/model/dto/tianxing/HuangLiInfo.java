package top.yueshushu.learn.extension.model.dto.tianxing;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 黄历信息
 * @Author yuejianli
 * @Date 2022/6/5 7:33
 **/
@Data
public class HuangLiInfo implements Serializable {
    /**
     * 阳历 2022-06-11
     */
    private String gregoriandate;
    /**
     * 农历 2022-5-13
     */
    private String lunardate;
    /**
     * 农历节日 lunar_festival
     */
    private String lunar_festival;
    /**
     * 阳历节日
     */
    private String festival;
    /**
     * 宜: 除服.疗病.出行.拆卸.入宅
     */
    private String fitness;
    /**
     * 禁: 求官.上任.开张.搬家.探病
     */
    private String taboo;
    /**
     * 申位:  喜神：西北 福神：西南 财神：东北阳贵：西南 阴贵：正北
     */
    private String shenwei;
    /**
     * 胎神:  碓磨莫移动,厕道莫修移,孕身胎神在房内北停留5天
     */
    private String taishen;
    /**
     * 冲煞：  羊日冲(己丑)牛
     */
    private String chongsha;
    /**
     * 岁煞:  岁煞西
     */
    private String suisha;
    /**
     * 五行甲子  金
     */
    private String wuxingjiazi;
    /**
     * 五行年:  金箔金
     */
    private String wuxingnayear;
    /**
     * 五行月: 天河水
     */
    private String wuxingnamonth;
    /**
     * 星宿: 北方女土蝠-凶
     */
    private String xingsu;
    /**
     * 乙不栽植 未不服药
     */
    private String pengzu;
    /**
     * 除
     */
    private String jianshen;
    /**
     * 天干地支年: 壬寅
     */
    private String tiangandizhiyear;
    /**
     * 天干地支月: 丙午
     */
    private String tiangandizhimonth;
    /**
     * 天干地支天: 乙未
     */
    private String tiangandizhiday;
    /**
     * 仲夏
     */
    private String lmonthname;
    /**
     * 生肖: 虎
     */
    private String shengxiao;
    /**
     * 农历天： 五月
     */
    private String lubarmonth;
    /**
     * 农历月： 十三
     */
    private String lunarday;
    /**
     * 节气:
     */
    private String jieqi;

}
