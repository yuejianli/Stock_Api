package top.yueshushu.learn.api.responseparse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.BaseTradeRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName:DefaultResponseParser
 * @Description 默认的解析器
 * @Author 岳建立
 * @Date 2022/1/2 21:51
 * @Version 1.0
 **/
@Service("dataListResponseParser")
public class DataListResponseParser implements ResponseParser {
    @Override
    public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
        TradeResultVo<T> resultVo = JSON.parseObject(content, new TypeReference<TradeResultVo<T>>() {
        });
        if (resultVo.getSuccess()) {
            List<T> list = resultVo.getData();
            ArrayList<T> newList = new ArrayList<>();
            if (list != null) {
                list.forEach(d -> {
                    String text = JSON.toJSONString(d);
                    T t = JSON.parseObject(text, responseType);
                    newList.add(t);
                });
            }
            resultVo.setData(newList);
        } else {
            resultVo.setData(Collections.emptyList());
        }
        return resultVo;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public int version() {
        return BaseTradeRequest.VERSION_DATA_LIST;
    }
}
