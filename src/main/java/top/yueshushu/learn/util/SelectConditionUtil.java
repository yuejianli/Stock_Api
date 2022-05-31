package top.yueshushu.learn.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Yue Jianli
 * @date 2022-04-21
 **/
public class SelectConditionUtil {

    /**
     * Long 验证空值
     * @date 2021/10/11 16:49
     * @author zk_yjl
     * @param value Long 类型的空值
     * @return boolean
     */
    public static boolean longIsNullOrZero(Long value){
        return null == value || value == 0;
    }
    /**
     * Integer 验证空值
     * @date 2021/10/11 16:49
     * @author zk_yjl
     * @param value Integer 类型的值
     * @return boolean
     */
    public static boolean intIsNullOrZero(Integer value){
        return null == value || value == 0;
    }
    /**
     * String 验证空值
     * @date 2021/10/11 16:49
     * @author zk_yjl
     * @param value String 值
     * @return boolean
     */
    public static boolean stringIsNullOrEmpty(String value){
       return StringUtils.isBlank(value);
    }
    /**
     * 将 Integer 值转换成对应的 int 值。如果是空值，处理成 0
     * @date 2021/10/14 14:39
     * @author zk_yjl
     * @param value Integer 类型的值
     * @return int 将 Integer 值转换成对应的 int 值。如果是空值，处理成 0
     */
    public static int convertNullInt(Integer value){
        if(intIsNullOrZero(value)){
            return 0;
        }
        return value;
    }
    /**
     * 将 Long 值转换成对应的 long 值。如果是空值，处理成 0L
     * @date 2021/10/14 14:39
     * @author zk_yjl
     * @param value Long 类型的值
     * @return long 将 Long 值转换成对应的 long 值。如果是空值，处理成 0L
     */
    public static long convertNullLong(Long value){
        if(longIsNullOrZero(value)){
            return 0L;
        }
        return value;
    }
}
