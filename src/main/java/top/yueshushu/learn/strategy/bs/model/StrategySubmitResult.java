package top.yueshushu.learn.strategy.bs.model;

import lombok.Data;
import top.yueshushu.learn.api.request.SubmitRequest;

import java.io.Serializable;

@Data
public class StrategySubmitResult extends SubmitRequest implements Serializable {
    private String relatedDealCode;
}
