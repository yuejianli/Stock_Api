package top.yueshushu.learn.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.*;
import top.yueshushu.learn.api.response.AuthenticationResponse;
import top.yueshushu.learn.config.TradeClient;
import top.yueshushu.learn.service.AbstractTradeApiService;
import top.yueshushu.learn.service.ConfigService;
import top.yueshushu.learn.service.TradeService;

import java.util.*;

@Service
public class TradeApiServiceImpl extends AbstractTradeApiService {

    public static final List<String> IgnoreList = Arrays.asList("class", "userId", "method");

    private final ResponseParser revokeResponseParser = new ResponseParser() {
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
            return "revokeResponseParser";
        }
    };

    private final ResponseParser v3ReponseParser = new ResponseParser() {
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
            return "v3ReponseParser";
        }
    };

    private final ResponseParser defaultReponseParser = new ResponseParser() {
        @Override
        public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
            TradeResultVo<T> resultVo = JSON.parseObject(content, new TypeReference<TradeResultVo<T>>() {});
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
            return "defaultReponseParser";
        }
    };

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeClient tradeClient;

    @Autowired
    private ConfigService configService;

    @Override
    public <T> TradeResultVo<T> send(BaseTradeRequest request, TypeReference<T> responseType) {
        return send(request, responseType, false);
    }

    @Override
    public <T> TradeResultVo<T> sendList(BaseTradeListRequest request, TypeReference<T> responseType) {
        return send(request, responseType, true);
    }

    private String getMockData(BaseTradeRequest request) {
        if (request instanceof SubmitRequest) {
            return "{\"Message\":null,\"Status\":0,\"Data\":[{\"Wtbh\": \"" + System.currentTimeMillis() + "\"}]}";
        } else if (request instanceof GetAssetsRequest) {
            return "{\"Message\":null,\"Status\":0,\"Data\":[{\"Zzc\": \"100000\", \"Kyzj\": \"50000\", \"Kqzj\": \"80000\", \"Djzj\": \"20000\" }]}";
        }
        return "{\"Message\":null,\"Status\":0,\"Data\":[]}";
    }

    private <T> TradeResultVo<T> send(BaseTradeRequest request, TypeReference<T> responseType, boolean isSendList) {
        ResponseParser responseParse = getResponseParser(request);
         return null;
    }

    @Override
    public TradeResultVo<AuthenticationResponse> authentication(AuthenticationRequest request) {
       return null;
    }

    private String getValidateKey(String content) {
        String key = "input id=\"em_validatekey\" type=\"hidden\" value=\"";
        int inputBegin = content.indexOf(key) + key.length();
        int inputEnd = content.indexOf("\" />", inputBegin);
        String validateKey = content.substring(inputBegin, inputEnd);
        return validateKey;
    }

    private ResponseParser getResponseParser(BaseTradeRequest request) {
       return null;
    }

    private Map<String, Object> getParams(Object request) {
        Map<Object, Object> beanMap = new BeanMap(request);
        HashMap<String, Object> params = new HashMap<>();
        beanMap.entrySet().stream().filter(entry -> !TradeApiServiceImpl.IgnoreList.contains(entry.getKey()))
                .forEach(entry -> params.put(String.valueOf(entry.getKey()), entry.getValue()));
        return params;
    }

    private String getUrl(BaseTradeRequest request) {
      return null;
    }

    private Map<String, String> getHeader(BaseTradeRequest request) {
      return null;
    }

    private static interface ResponseParser {
        <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType);
        String name();
    }

}
