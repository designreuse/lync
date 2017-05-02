package com.lync.core.shiro;


import com.lync.common.vo.ShiroUser;
import com.lync.core.toolbox.kit.SpringKit;
import com.lync.domain.primary.User;
import com.lync.service.UserService;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by breeze on 2017/2/25.
 */

public class DefaultShiroFactory implements IShiro{

    public User user(String username) {
        UserService userService = (UserService) SpringKit.getBean( "userService" );
        User user = userService.findByUsername(username);
        // 账号不存在
        if (null == user) {
            throw new UnknownAccountException();
        }
        // 账号被冻结
        if (user.getStatu()==2) {
            throw new DisabledAccountException();
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {
        return new ShiroUser(user.getId(), user.getUsername(), user.getRoleList());
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }
}
