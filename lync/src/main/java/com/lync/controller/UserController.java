package com.lync.controller;

import com.alibaba.fastjson.JSONObject;
import com.lync.common.base.BaseController;
import com.lync.core.constant.Const;
import com.lync.core.toolbox.kit.JsonKit;
import com.lync.core.toolbox.result.Result;
import com.lync.domain.primary.Role;
import com.lync.domain.primary.User;
import com.lync.service.RoleService;
import com.lync.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by breeze on 2017/3/1.
 */
@RestController
public class UserController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    /*管理员添加用户*/
    @PostMapping("/admin/user/addUser")
    public Result addUser(@RequestBody String requestBody,@RequestParam("roleid")String roleid) {
        try{
            logger.info( "添加用户....." );
            JSONObject userinfo = JsonKit.parse(requestBody);
            String username= (String) JsonKit.getObject(userinfo,"username");
            User user = new User();
            user.setUsername(username);
            user.setStatu(1);
            user.setCreateTime(Const.TIMESTAMP);
            List<Role> roleList =new ArrayList<>();
            Role role = roleService.findById(Long.valueOf(roleid));
            roleList.add(role);
            user.setRoleList(roleList);
            User byUsername = userService.findByUsername(username);
            if(byUsername!=null){
                return error("此用户已经存在");
            }
            boolean flag = userService.addUser(user);
            if(flag){
                return success("添加用户成功");
            }
            return error("添加用户失败");
        }catch (Exception e){
            logger.error("未知错误,请联系管理员!", e);
            return error("未知错误,请联系管理员");
        }
    }

    /*管理员删除用户*/
    @DeleteMapping("/admin/user/delete")
    public Result delUser(@RequestParam("user_id") Long id) {
        try{
            boolean flag = userService.deleteUserById(Long.valueOf(id));
            if(flag){
                return success("删除用户成功");
            }
            return success("删除用户失败");
        }catch (Exception e){
            logger.error("未知错误,请联系管理员!", e);
            return error("未知错误,请联系管理员");
        }
    }

    /*管理员修改角色*/
    @PutMapping("/admin/user/update")
    public Result updateUser(@RequestParam("user_id") Long user_id,@RequestBody String requestBody) {
        try{
            JSONObject jsonObject = JsonKit.parse(requestBody);
            String action= (String) JsonKit.getObject(jsonObject,"action");
            String role_id= (String) JsonKit.getObject(jsonObject,"role_id");
            boolean flag=false;
            if("add".equals(action)){
                flag=userService.addUserRole(user_id,Long.valueOf(role_id));
            }else if("modify".equals(action)){
                flag=userService.updateUserRole(Long.valueOf(role_id),user_id);
            }
            if (flag){
                return success("修改成功");
            }
            return success("修改失败");
        }catch (Exception e){
            logger.error("未知错误,请联系管理员!", e);
            return error("未知错误,请联系管理员");
        }
    }





}
