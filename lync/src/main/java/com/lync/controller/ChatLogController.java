package com.lync.controller;

import com.lync.common.base.BaseController;
import com.lync.common.vo.ShiroUser;
import com.lync.core.shiro.ShiroKit;
import com.lync.core.toolbox.result.Result;
import com.lync.domain.primary.Role;
import com.lync.service.ChatLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by breeze on 2017/3/1.
 */
@RestController
public class ChatLogController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ChatLogController.class);
    @Autowired
    private ChatLogService chatLogService;
     /*会话展示列表*/
    @GetMapping("/user/chatlog")
    public Result chatlog(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "30") Integer size) {
        try {
            ShiroUser user = ShiroKit.getUser();
            String username=ShiroKit.getUser().getUsername();
            List<Role> roleList = user.getRoleList();
            boolean flag=false;
            for(Role r:roleList){
                String rolename = r.getRolename();
                if("admin".equalsIgnoreCase(rolename)||"qa".equalsIgnoreCase(rolename)){
                    flag=true;
                }
            }
            Sort sort = new Sort(Sort.Direction.DESC, "time");
            Pageable pageable = new PageRequest(page-1, size, sort);
            if(flag){
                //查询所有
                return success(chatLogService.findAll(pageable));
            }else{
                //查询个人回话
                return success(chatLogService.findChatLogForUser(username,pageable));
            }
        }catch (Exception e){
            logger.error("未知错误",e);
            return error("未知错误");
        }
    }



}
