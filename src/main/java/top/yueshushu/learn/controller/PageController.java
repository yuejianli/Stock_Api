package top.yueshushu.learn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName:PageController
 * @Description 页面跳转使用的Controller
 * @Author 岳建立
 * @Date 2021/11/6 10:39
 * @Version 1.0
 **/
@Controller
@Slf4j
public class PageController {

    @Value("${login.noLoginUrl}")
    private String noLoginUrl;

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "page";
    }

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/redirect")
    public void redirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.sendRedirect(noLoginUrl);
        } catch (IOException e) {
            log.error("异常信息", e);
        }
    }
}
