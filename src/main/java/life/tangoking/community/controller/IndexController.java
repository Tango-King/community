package life.tangoking.community.controller;

import life.tangoking.community.mapper.UserMapper;
import life.tangoking.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {

        // 从请求中获得Cookies
        Cookie[] cookies = request.getCookies();
        // 遍历Cookies找到登录用户的token信息
        if (cookies == null || cookies.length == 0) {
            return "index";
        }
        for (Cookie cookie: cookies) {
            if(!"token".equals(cookie.getName())) {
                continue;
            }
            User user = userMapper.findUserByToken(cookie.getValue());
            if (user !=  null) {
                request.getSession().setAttribute("user", user);
                break;
            }
        }
        return "index";
    }
}
