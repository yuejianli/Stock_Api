package top.yueshushu.learn.interceptor;

/**
 * @author yuejianli
 * @Date: 2022-05-20
 */
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.UserDo;
import top.yueshushu.learn.util.JwtUtils;
import top.yueshushu.learn.util.RedisUtil;
import top.yueshushu.learn.util.ThreadLocalUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;


@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    private String httpHeaderName = "Authorization";

    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(AuthToken.class) != null ||
                handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {

            String token = request.getHeader(httpHeaderName);
            String ip = request.getHeader(Const.X_REAL_IP);
            UserDo currentUserDo = null;
            if (StringUtils.isNotBlank(token)) {
                currentUserDo = redisUtil.get(token);
                if (currentUserDo == null) {
                    Claims claims;
                    try{
                        claims = jwtUtils.parseJwt(token);
                    }catch (ExpiredJwtException e){
                        claims = e.getClaims();
                    }catch (MalformedJwtException e){
                        isFalse(response);
                        return false;
                    }
                    long timestamp = Long.parseLong(claims.get("timestamp").toString());
                    long time = System.currentTimeMillis() - timestamp;
                    if (time < 12 * 3600 * 1000) {
                        isUnValid(response);
                        return false;
                    }
                }
            }
            if (StringUtils.isBlank(token) || currentUserDo == null) {
                isFalse(response);
                return false;
            }

            String userName = currentUserDo.getAccount();
            ThreadLocalUtils.put("userName", userName);
            ThreadLocalUtils.put("ip", ip == null ? request.getRemoteAddr() : ip);
            ThreadLocalUtils.put("user", currentUserDo);
            ThreadLocalUtils.put("token",token);

            long tokeBirthTime = Long.parseLong(redisUtil.get(token + userName));
            long diff = System.currentTimeMillis() - tokeBirthTime;

            if (diff < Const.TOKEN_EXPIRE_TIME * 1000) {
                long newBirthTime = System.currentTimeMillis();
                redisUtil.set(token + userName, Long.toString(newBirthTime));
                request.setAttribute(REQUEST_CURRENT_KEY, userName);
                return true;
            } else {
                isFalse(response);
                return false;
            }

        }
        request.setAttribute(REQUEST_CURRENT_KEY, null);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadLocalUtils.release();
    }

    private void isFalse(HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        PrintWriter out = null;
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            jsonObject.put("code", ResultCode.LOGIN_EXPIRE.getCode());
            jsonObject.put("message", ResultCode.LOGIN_EXPIRE.getMessage());
            //添加其余的两个属性  success和 data, 解决 PDA端无法翻译 退出的问题。 @zk_yjl
            jsonObject.put("data","");
            jsonObject.put("success",false);
            out = response.getWriter();
            out.println(jsonObject);
        } catch (Exception e) {
            log.error("发生异常{}", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    private void isUnValid(HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        PrintWriter out = null;
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            jsonObject.put("code", ResultCode.ACCOUNT_IS_OFFLINE.getCode());
            jsonObject.put("message", ResultCode.ACCOUNT_IS_OFFLINE.getMessage());
            //添加其余的两个属性  success和 data, 解决 PDA端无法翻译 退出的问题。 @zk_yjl
            jsonObject.put("data","");
            jsonObject.put("success",false);
            out = response.getWriter();
            out.println(jsonObject);
        } catch (Exception e) {
            log.error("发生异常", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
