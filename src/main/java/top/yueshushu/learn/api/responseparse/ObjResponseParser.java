package top.yueshushu.learn.api.responseparse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.BaseTradeRequest;

import java.util.ArrayList;

/**
 * @ClassName:DefaultResponseParser
 * @Description 默认的解析器
 * @Author 岳建立
 * @Date 2022/1/2 21:51
 * @Version 1.0
 **/
@Service("objResponseParser")
public class ObjResponseParser implements ResponseParser {
    @Override
    public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
        T t = JSON.parseObject(content, responseType);
        ArrayList<T> newList = new ArrayList<>();
        newList.add(t);

        TradeResultVo<T> resultVo = new TradeResultVo<>();
        resultVo.setData(newList);

        return resultVo;
    }

    @Override
    public String name() {
        return "objResponseParser";
    }

    @Override
    public int version() {
        return BaseTradeRequest.VERSION_OBJ;
    }
}
