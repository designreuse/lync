package com.lync.core.shiro;

import com.lync.common.vo.ShiroUser;
import com.lync.core.toolbox.kit.SpringKit;
import com.lync.domain.primary.User;
import com.lync.service.UserService;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
public class LdapShiroFactory implements IShiro{
    @Override
    public User user(String username) {
        UserService userService = (UserService) SpringKit.getBean( "userService" );
        User user = userService.findByUsername(username);
        if(user==null){
            throw new UnknownAccountException();
        }
       return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {
        return new ShiroUser(user.getId(), user.getUsername(), user.getRoleList());
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {

        return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), realmName);
    }
}
