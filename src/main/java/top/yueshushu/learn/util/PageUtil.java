package top.yueshushu.learn.util;

import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName:PageUtil
 * @Description 自定义后端分页
 * @Author 岳建立
 * @Date 2022/1/3 11:47
 * @Version 1.0
 **/
public class PageUtil {

    /**
     * 开始分页
     *
     * @param list
     * @param pageNum  页码
     * @param pageSize 每页多少条数据
     * @return
     */
    public static List startPage(List list, Integer pageNum, Integer pageSize) {
        if(list == null){
            return Collections.emptyList();
        }
        if(list.size() == 0){
            return Collections.emptyList();
        }
        //记录总数
        Integer count = list.size();
        //页数
        Integer pageCount = 0;
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        //开始索引
        int fromIndex = 0;
        //结束索引
        int toIndex = 0;

        if(pageNum > pageCount){
            pageNum = pageCount;
        }
        if (!pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List pageList = Optional.of(list.subList(fromIndex, toIndex)).orElse(Collections.emptyList());

        return pageList;
    }

    /**
     * 进行返回
     * @param list
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static OutputResult pageResult(List list, Integer pageNum, Integer pageSize) {
        return OutputResult.buildSucc(new PageResponse((long) list.size(),
                startPage(list,pageNum,pageSize)));
    }
}