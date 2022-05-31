package top.yueshushu.learn.util;
import top.yueshushu.learn.domain.UserDo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author yuejianli
 * 全局性线程处理
 */
public class ThreadLocalUtils {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 存储
     */
    public static void put(String key, Object value) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(1,1);
        }
        map.put(key, value);
        THREAD_LOCAL.set(map);
    }

    /**
     * 取值
     */
    public static <T> T get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map != null) {
            return (T) map.get(key);
        }
        return null;
    }

    /**
     * 获取当前用户
     */
    public static UserDo getUser() {
        Map<String, Object> map = THREAD_LOCAL.get();
        return (UserDo) map.get("user");
    }

    public static void release() {
        THREAD_LOCAL.remove();
    }

    public static String getToken() {
        return get("token");
    }

    /**
     * 如果当前用户没有登录，则默认为1
     * todo 需要处理，改成必须登录才可以使用的。
     * @return
     *
     */
    public static Integer getUserId() {
        Integer userId = get("userId");
        return Optional.ofNullable(
                userId
        ).orElse(1);
    }
}
