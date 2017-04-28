package com.lync.core.shiro;

import com.lync.common.vo.ShiroUser;
import com.lync.core.toolbox.kit.SpringKit;
import com.lync.domain.primary.Permission;
import com.lync.domain.primary.Role;
import com.lync.domain.primary.User;
import com.lync.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by breeze on 2017/2/25.
 */

public class ShiroAuthorRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(ShiroAuthorRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info( "shiro2鉴权开始" );
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        System.out.println(shiroUser.getUsername());
        UserService userService = SpringKit.getBean(UserService.class);
        User user = userService.findByUsername(shiroUser.getUsername());

        Set<String> roleNameSet = new HashSet<>();
        Set<String> urlSet = new HashSet<>();
        List<Role> roleList = user.getRoleList();
        for (Role r:roleList){
            roleNameSet.add(r.getRolename());
            List<Permission> permissions = r.getPermissionList();
            if (null != permissions) {
                for (Permission p : permissions) {
                    if (!StringUtils.isEmpty(p.getPermissionname())) {
                        urlSet.add(p.getPermissionname());
                        System.out.println(p.getPermissionname()+"  permissionname");
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(urlSet);
        info.addRoles(roleNameSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken){
        return null;
    }
}
