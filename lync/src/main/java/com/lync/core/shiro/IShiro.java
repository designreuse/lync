package com.lync.core.shiro;

import com.lync.common.vo.ShiroUser;
import com.lync.domain.primary.User;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

/**
 * Created by breeze on 2017/2/25.
 */
public interface IShiro {
    User user(String username);
    ShiroUser shiroUser(User user);
    SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName);
}
