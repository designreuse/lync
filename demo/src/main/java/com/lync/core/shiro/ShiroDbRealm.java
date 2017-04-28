package com.lync.core.shiro;

import com.lync.common.vo.ShiroUser;
import com.lync.core.toolbox.kit.SpringKit;
import com.lync.domain.primary.Permission;
import com.lync.domain.primary.Role;
import com.lync.domain.primary.User;
import com.lync.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by breeze on 2017/2/25.
 */

public class ShiroDbRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);

    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info( "shiro鉴权开始" );
        IShiro shiroFactory = ShiroManager.me().getDefaultShiroFactory();
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        String username = shiroUser.getUsername();
        UserService userService = SpringKit.getBean(UserService.class);
        User user = userService.findByUsername(username);
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info( "shiro认证开始" );

        IShiro shiroFactory = ShiroManager.me().getDefaultShiroFactory();
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = shiroFactory.user(token.getUsername());
        ShiroUser shiroUser = shiroFactory.shiroUser(user);//根据用户名把User的相关信息存起来，方便以后使用
        SimpleAuthenticationInfo info = shiroFactory.info(shiroUser, user, getName());
        logger.info( "shiro认证结束" );
        return info;
    }

    /**
     * 设置认证加密方式
     */
    @PostConstruct
    public void setCredentialMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        credentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        setCredentialsMatcher(credentialsMatcher);
    }
}
