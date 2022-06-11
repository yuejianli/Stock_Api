package top.yueshushu.learn.extension.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.yueshushu.learn.extension.model.dto.gaodeweather.WeatherResponse;
import top.yueshushu.learn.extension.model.dto.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.dto.shici.PoemResponse;
import top.yueshushu.learn.extension.model.dto.tianxing.*;
import top.yueshushu.learn.extension.service.ExtFunctionService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-10
 */
@Service
public class ExtFunctionServiceImpl implements ExtFunctionService {
	
	@Resource
	private RestTemplate restTemplate;
	
	/*
	 使用的key
	 */
	/**
	 * towBufferfly  129@qq.com   1234abcd
	 */
	@Value("${tianxing.key1}")
	private String tianxingKey1;
	
	/**
	 * 王者 wangzheyuhou  2048146495@qq.com   1234abcd
	 */
	@Value("${tianxing.key2}")
	private String tianxingKey2;
	
	@Override
	/**
	 * 获取天气情况
	 *{
	 *     "count":1,
	 *     "forecasts":[
	 *         {
	 *             "adcode":"522701",
	 *             "casts":[
	 *                 {
	 *                     "date":"2022-06-11",
	 *                     "daypower":"≤3",
	 *                     "daytemp":"23",
	 *                     "dayweather":"阴",
	 *                     "daywind":"北",
	 *                     "nightpower":"≤3",
	 *                     "nighttemp":"19",
	 *                     "nightweather":"小雨",
	 *                     "nightwind":"北",
	 *                     "week":"6"
	 *                 },
	 *                 {
	 *                     "date":"2022-06-12",
	 *                     "daypower":"≤3",
	 *                     "daytemp":"26",
	 *                     "dayweather":"中雨",
	 *                     "daywind":"北",
	 *                     "nightpower":"≤3",
	 *                     "nighttemp":"19",
	 *                     "nightweather":"中雨",
	 *                     "nightwind":"北",
	 *                     "week":"7"
	 *                 },
	 *                 {
	 *                     "date":"2022-06-13",
	 *                     "daypower":"≤3",
	 *                     "daytemp":"22",
	 *                     "dayweather":"小雨",
	 *                     "daywind":"北",
	 *                     "nightpower":"≤3",
	 *                     "nighttemp":"18",
	 *                     "nightweather":"阴",
	 *                     "nightwind":"北",
	 *                     "week":"1"
	 *                 },
	 *                 {
	 *                     "date":"2022-06-14",
	 *                     "daypower":"≤3",
	 *                     "daytemp":"30",
	 *                     "dayweather":"多云",
	 *                     "daywind":"北",
	 *                     "nightpower":"≤3",
	 *                     "nighttemp":"20",
	 *                     "nightweather":"多云",
	 *                     "nightwind":"北",
	 *                     "week":"2"
	 *                 }
	 *             ],
	 *             "city":"都匀市",
	 *             "province":"贵州",
	 *             "reporttime":"2022-06-11 06:01:11"
	 *         }
	 *     ],
	 *     "info":"OK",
	 *     "infocode":10000,
	 *     "status":1
	 * }
	 * @param city 城市
	 */
	public WeatherResponse getWeather(String city) {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", "3060ad090681f5c501f5ca3cf5798b53");
		paramMap.put("city", city);
		paramMap.put("extensions", "all");
		WeatherResponse weatherResponse = restTemplate.getForEntity(
				"https://restapi.amap.com/v3/weather/weatherInfo?key={key}&city={city}&extensions={extensions}",
				WeatherResponse.class,
				paramMap
		).getBody();
		return weatherResponse;
	}

	/**
	 * 获取每日一句
	 * {
	 * "assign_date":"2022-06-05",
	 * "author":"Helen Keller",
	 * "content":"True happiness is not attained through self-gratification, but through fidelity to a worthy purpose.",
	 * "translation":"真正的幸福不是通过自我满足获得的，而是来自于对有价值目标的忠诚。"
	 * }
	 */
	@Override
	public TranslateResponse getTranslate() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("date", DateUtil.format(DateUtil.date(), "yyyyMMdd"));
		TranslateResponse translateResponse = restTemplate.getForEntity(
				"https://apiv3.shanbay.com/weapps/dailyquote/quote/?date={date}",
				TranslateResponse.class,
				paramMap
		).getBody();
		return translateResponse;
	}

	/**
	 * 获取一句诗词
	 {
	 "author":"令狐楚",
	 "category":"古诗文-动物-写马",
	 "content":"弓背霞明剑照霜，秋风走马出咸阳。",
	 "origin":"少年行四首·其三"
	 }
	 */
	@Override
	public PoemResponse getPoem() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		return restTemplate.getForEntity(
				"https://v1.jinrishici.com/all.json",
				PoemResponse.class,
				paramMap
		).getBody();
	}

	/**
	 * 获取对联信息
	 *          {
	 * 	 "code":200,
	 * 	 "msg":"success",
	 * 	 "newslist":[
	 *     {
	 * 	 "content":"抚辰逢地腊，建午届天中"
	 *     }
	 * 	 ]
	 *     }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getCouplets() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> couplets = restTemplate.getForEntity(
				"http://api.tianapi.com/duilian/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return couplets;
	}

	/**
	 * 获取古典诗句
	 * {
	 *             "content":"道虽迩，不行不至；事虽小，不为不成。",
	 *             "source":"荀子·修身"
	 *         }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getClassical() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> classical = restTemplate.getForEntity(
				"http://api.tianapi.com/gjmj/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return classical;
	}

	/**
	 * 获取经典对话
	 * {
	 *             "dialogue":"我看得见死人在你的梦里吗不是，是现实中他们像我们一样走来走去他们彼此都看不见他们只看得到他们想看的他们不知道自己已经死了经常见到到处都有他",
	 *             "english":"I see dead peopleIn your dreams？“ NO “Walking around like regular people.They don't see each other.They only see what they wanna see.They don't know they're dead .All the time.They",
	 *             "source":"灵异第六感 The sixth sense",
	 *             "type":0
	 *   }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getDialogue() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> dialogue = restTemplate.getForEntity(
				"http://api.tianapi.com/dialogue/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return dialogue;
	}

	/**
	 * 获取彩虹屁
	 *{
	 *             "content":"喜欢一切可爱的人与事，悦耳的风铃、伸懒腰的猫咪、叽叽喳喳的小鸟、在风里翻飞的书页上的漂亮的文字。喜欢世间万物，但我更喜欢你，因为你是可爱本身。"
	 *}
	 */
	@Override
	public TianXingResponse<TianXingInfo> getCaiHongPi() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> caihongpi = restTemplate.getForEntity(
				"http://api.tianapi.com/caihongpi/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return caihongpi;
	}

	/**
	 * 获取百科题库信息
	 * {
	 *             "title":"中国历史上，推行“一条鞭法”的改革家是谁？",
	 *             "answerA":"商鞅",
	 *             "answerB":"田文镜",
	 *             "answerC":"张居正",
	 *             "answerD":"王安石",
	 *             "answer":"C",
	 *             "analytic":""
	 *         }
	 */
	@Override
	public TianXingResponse<BaiKeTiKuInfo> getBaiKeTiKu() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<BaiKeTiKuInfo> baiketiku = restTemplate.getForEntity(
				"http://api.tianapi.com/baiketiku/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return baiketiku;
	}

	/**
	 * 获取英语信息
	 * {
	 *             "id":3507,
	 *             "content":"Use what talents you possess: the woods would be very silent if no birds sang there except those that sang best.",
	 *             "note":"天生我材必有用。若只有声音最悦耳的鸟儿在歌唱，树林该多沉寂。",
	 *             "source":"",
	 *             "tts":"",
	 *             "imgurl":"",
	 *             "date":""
	 *         }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getEnglish() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> english = restTemplate.getForEntity(
				"http://api.tianapi.com/everyday/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return english;
	}
	
	
	/**
	 * 获取早安的信息
	 * {
	 * "content": "用努力去喂养梦想，愿跌倒不哭，明媚如初，早安。"
	 * }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getZaoAn() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> zaoan = restTemplate.getForEntity(
				"http://api.tianapi.com/zaoan/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return zaoan;
	}

	/**
	 * 获取晚安的信息
	 * {
	 *             "content":"在我年轻的时候，不知道什么是恐惧，但偏偏是你的温柔，让我害怕。晚安！"
	 * }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getWanAn() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> wanan = restTemplate.getForEntity(
				"http://api.tianapi.com/wanan/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return wanan;
	}

	/**
	 * 获取十万个为什么
	 * {
	 *             "typeid":11,
	 *             "title":"动物妈妈如何照顾它们的小宝宝？",
	 *             "content":"　　因为刚出生的小动物都很软弱，没有抵抗其他动物侵犯的能力。动物妈妈除了哺乳和喂食外，还教它们学习躲避敌害和觅食。这样，宝宝长大后才能生存下去。”"
	 *         }
	 *     ]
	 * }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getTenWhy() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<TianXingInfo> tenwhy = restTemplate.getForEntity(
				"http://api.tianapi.com/tenwhy/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return tenwhy;
	}

	/**
	 * 获取字迷信息
	 * {
	 *             "content":"九只鸟(打一字)",
	 *             "answer":"鸠",
	 *             "reason":"九  + 鸟   = 鸠"
	 *         }
	 */
	@Override
	public TianXingResponse<ZiMiInfo> getZiMi() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey1);
		TianXingResponse<ZiMiInfo> zimi = restTemplate.getForEntity(
				"http://api.tianapi.com/zimi/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return zimi;
	}


	/**
	 * 获取文化谚语
	 * {
	 * "front":"道虽近，不行不至",
	 * "behind":"事虽小，不做不成。 "
	 * }
	 */
	@Override
	public TianXingResponse<ProverbInfo> getProverb() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		TianXingResponse<ProverbInfo> proverb = restTemplate.getForEntity(
				"http://api.tianapi.com/proverb/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return proverb;
	}

	/**
	 * 获取成语
	 * {
	 * "question": "原指铺张词藻或畅所欲言。现用来指大发议论。",
	 * "abbr": "DFJC",
	 * "answer": "大放厥词",
	 * "pinyin": "dà  fàng  jué  cí",
	 * "source": "唐·韩愈《祭柳子厚文》：“玉佩琼琚，大放厥词。”",
	 * "study": "也许他认定我年轻无知，才毫无顾虑地在我面前～。（郭良蕙《焦点》１０）"
	 * }
	 *
	 * @return
	 */
	@Override
	public TianXingResponse<ChengYuInfo> getChengYu() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		TianXingResponse<ChengYuInfo> chengyu = restTemplate.getForEntity(
				"http://api.tianapi.com/caichengyu/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return chengyu;
	}

	/**
	 * {
	 * "content": "鸡肠子用醋和碱水洗净，焙干，研成细粉，每日服一付鸡肠粉，淡白矾水送下，连服七日为一疗程，可治遗尿。"
	 * }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getQiaoMen() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		TianXingResponse<TianXingInfo> qiaomen = restTemplate.getForEntity(
				"http://api.tianapi.com/qiaomen/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return qiaomen;
	}

	/**
	 * {
	 * "quest": "折枝桂花献亲人    提示：打字一",
	 * "answer": "佳"
	 * }
	 */
	@Override
	public TianXingResponse<ZiMiInfo> getMiYu() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		TianXingResponse<ZiMiInfo> miyu = restTemplate.getForEntity(
				"http://api.tianapi.com/riddle/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return miyu;
	}

	/**
	 * {
	 * "content": "汉兵已略地，四方楚歌声。大王意气尽，贱妾何聊生。",
	 * "source": "和项王歌",
	 * "author": "虞姬"
	 * }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getQingShi() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		TianXingResponse<TianXingInfo> qingshi = restTemplate.getForEntity(
				"http://api.tianapi.com/qingshi/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return qingshi;
	}

	/**
	 * {
	 * "content": "我们是国家的主人，应该处处为国家着想。",
	 * "author": "雷锋"
	 * }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getMingYan() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		TianXingResponse<TianXingInfo> qingshi = restTemplate.getForEntity(
				"http://api.tianapi.com/mingyan/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return qingshi;
	}

	/**
	 * {
	 * "content": "我们现在是朋友了么？\r\n是啊。\r\n那以后就不是了。\r\n为什么？\r\n因为你是我女朋友。"
	 * }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getSayLove() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		TianXingResponse<TianXingInfo> qingshi = restTemplate.getForEntity(
				"http://api.tianapi.com/saylove/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return qingshi;
	}

	/**
	 * {
	 * "gregoriandate": "2022-06-11",
	 * "lunardate": "2022-5-13",
	 * "lunar_festival": "关羽诞",
	 * "festival": "",
	 * "fitness": "除服.疗病.出行.拆卸.入宅",
	 * "taboo": "求官.上任.开张.搬家.探病",
	 * "shenwei": "喜神：西北 福神：西南 财神：东北阳贵：西南 阴贵：正北 ",
	 * "taishen": "碓磨莫移动,厕道莫修移,孕身胎神在房内北停留5天",
	 * "chongsha": "羊日冲(己丑)牛",
	 * "suisha": "岁煞西",
	 * "wuxingjiazi": "金",
	 * "wuxingnayear": "金箔金",
	 * "wuxingnamonth": "天河水",
	 * "xingsu": "北方女土蝠-凶",
	 * "pengzu": "乙不栽植 未不服药",
	 * "jianshen": "除",
	 * "tiangandizhiyear": "壬寅",
	 * "tiangandizhimonth": "丙午",
	 * "tiangandizhiday": "乙未",
	 * "lmonthname": "仲夏",
	 * "shengxiao": "虎",
	 * "lubarmonth": "五月",
	 * "lunarday": "十三",
	 * "jieqi": ""
	 * }
	 */
	@Override
	public TianXingResponse<HuangLiInfo> getHuangLi() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		paramMap.put("date", DateUtil.format(DateUtil.date(), "yyyy-MM-dd"));
		TianXingResponse<HuangLiInfo> huangLi = restTemplate.getForEntity(
				"http://api.tianapi.com/lunar/index?key={key}&date={date}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return huangLi;
	}

	/**
	 * {
	 * "quest": "江边上洗萝卜",
	 * "result": "一个个来（比喻按次序地进行。）"
	 * }
	 */
	@Override
	public TianXingResponse<XieHouYuInfo> getXieHouYu() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		TianXingResponse<XieHouYuInfo> xieHouYu = restTemplate.getForEntity(
				"http://api.tianapi.com/xiehou/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return xieHouYu;
	}

	/**
	 * {
	 * "content": "树上有只小桃子，树下有只小猴子。
	 * 风吹桃树哗哗响，树上掉下小桃子。
	 * 桃子打着小猴子，猴子吃掉小桃子。"
	 * }
	 */
	@Override
	public TianXingResponse<TianXingInfo> getRaoKouLing() {
		Map<String, String> paramMap = new HashMap<>(2, 1.0f);
		paramMap.put("key", tianxingKey2);
		TianXingResponse<TianXingInfo> raoKouLing = restTemplate.getForEntity(
				"http://api.tianapi.com/rkl/index?key={key}",
				TianXingResponse.class,
				paramMap
		).getBody();
		return raoKouLing;
	}
}
