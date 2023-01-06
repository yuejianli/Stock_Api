package top.yueshushu.learn.api.responseparse;

import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.BaseTradeRequest;

import java.util.Collections;

/**
 * @ClassName:DefaultResponseParser
 * @Description 默认的解析器
 * @Author 岳建立
 * @Date 2022/1/2 21:51
 * @Version 1.0
 **/
@Service("msgResponseParser")
public class MsgResponseParser implements ResponseParser {
    @Override
    public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
        TradeResultVo<T> resultVo = new TradeResultVo<>();
        resultVo.setData(Collections.emptyList());
        resultVo.setStatus(TradeResultVo.STATUS_SUCCESS);
        resultVo.setMessage(content);
        return resultVo;
    }

    @Override
    public String name() {
        return "msgResponseParser";
    }

    @Override
    public int version() {
        return BaseTradeRequest.VERSION_MSG;
    }
}
