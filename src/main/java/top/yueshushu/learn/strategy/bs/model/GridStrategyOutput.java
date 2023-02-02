package top.yueshushu.learn.strategy.bs.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GridStrategyOutput extends BaseStrategyOutput implements Serializable {

    private List<String> revokeList;
    private List<StrategySubmitResult> submitList;
}
