package top.yueshushu.learn.response;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName:PageResponse
 * @Description 分页响应的信息
 * @Author 岳建立
 * @Date 2021/11/19 21:06
 * @Version 1.0
 **/
@Data
public class PageResponse<T> {
    private Long total;
    private List<T> list;
    public PageResponse(Long total,List<T> list) {
        this.total = Optional.ofNullable(total).orElse(0L);
        this.list = Optional.ofNullable(list).orElse(Collections.emptyList());
    }
    public static PageResponse emptyPageResponse(){
        return new PageResponse(0L,
                Collections.EMPTY_LIST);
    }
}
