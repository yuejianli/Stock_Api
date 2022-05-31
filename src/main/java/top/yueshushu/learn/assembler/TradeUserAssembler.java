package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.TradeUserDo;
import top.yueshushu.learn.entity.TradeUser;
import top.yueshushu.learn.mode.vo.TradeUserVo;

/**
 * @Description 交易用户转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface TradeUserAssembler {
    /**
     * 交易用户 domain 转换成实体entity
     * @param tradeUserDo 交易用户Do
     * @return 交易用户 domain 转换成实体entity
     */
    TradeUser doToEntity(TradeUserDo tradeUserDo);

    /**
     * 交易用户 entity 转换成 domain
     * @param tradeUser 交易用户
     * @return 交易用户 entity 转换成 domain
     */
    TradeUserDo entityToDo(TradeUser tradeUser);

    /**
     * 交易用户 entity 转换成 vo
     * @param tradeUser 交易用户
     * @return 交易用户 entity 转换成 vo
     */
    TradeUserVo entityToVo(TradeUser tradeUser);
}
