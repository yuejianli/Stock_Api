package top.yueshushu.learn.response;

import lombok.Data;
import lombok.ToString;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @author yuejianli
 */
@ToString
@Data
public class BaseResultCode {
    public static final BaseResultCode SUCCESS = new BaseResultCode(true, 20000, "操作成功");
    public static final BaseResultCode ALERT = new BaseResultCode(false, 30000, "传入信息有误");
    public static final BaseResultCode FAIL = new BaseResultCode(false, 50000, "操作失败");
    private boolean success;
    private int code;
    private String message;

    public BaseResultCode() {

    }

    public BaseResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static BaseResultCode[] getStaticFieldValues(Class clazz) {
        Reflections reflections = new Reflections(clazz);
        Set<BaseResultCode> monitorClasses = reflections.getSubTypesOf(clazz);
        return monitorClasses.toArray(new BaseResultCode[monitorClasses.size()]);
    }
}
