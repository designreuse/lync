package com.lync.controller;

import com.alibaba.fastjson.JSONObject;
import com.lync.common.base.BaseController;
import com.lync.core.shiro.ShiroKit;
import com.lync.core.toolbox.kit.JsonKit;
import com.lync.core.toolbox.result.Result;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Created by breeze on 2017/3/1.
 */
@RestController
public class LoginController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public Result verifyUser(@RequestBody String requestBody) {
        try{
            JSONObject userinfo = JsonKit.parse(requestBody);
            String username= (String) JsonKit.getObject(userinfo,"username");
            String password= (String) JsonKit.getObject(userinfo,"password");

            Subject currentUser = ShiroKit.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
            currentUser.login(token);
        }catch (AuthenticationException e) {
            logger.error("认证失败!", e);
            return error("认证失败");
        } catch (Exception e) {
            logger.error("未知错误,请联系管理员!", e);
            return error("未知错误,请联系管理员");
        }
        return success("登录成功");
    }


    @RequestMapping("/logout")
    public Result logout() {
        try {
            Subject currentUser = ShiroKit.getSubject();
            currentUser.logout();
        }catch (Exception e){
            logger.error("未知错误",e);
        }
        return success("登出成功");
    }

}
