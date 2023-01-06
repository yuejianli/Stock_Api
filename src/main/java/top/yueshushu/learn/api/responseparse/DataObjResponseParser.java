package top.yueshushu.learn.api.responseparse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.BaseTradeRequest;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @ClassName:DefaultResponseParser
 * @Description 默认的解析器
 * @Author 岳建立
 * @Date 2022/1/2 21:51
 * @Version 1.0
 **/
@Service("dataObjResponseParser")
public class DataObjResponseParser implements ResponseParser {
    @Override
    public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
        TradeResultVo<T> resultVo = new TradeResultVo<>();
        ArrayList<T> newList = new ArrayList<>();

        TradeResultVo<T> result = JSON.parseObject(content, new TypeReference<TradeResultVo<T>>() {
        });
        if (TradeResultVo.success(result.getStatus())) {
            String text = JSON.toJSONString(result.getData());
            T t = JSON.parseObject(text, responseType);
            newList.add(t);
        } else {
            resultVo.setData(Collections.emptyList());
        }

        resultVo.setMessage(result.getMessage());
        resultVo.setStatus(result.getStatus());
        resultVo.setData(newList);
        return resultVo;
    }

    @Override
    public String name() {
        return "defaultResponseParser";
    }

    @Override
    public int version() {
        return BaseTradeRequest.VERSION_DATA_OBJ;
    }
}
