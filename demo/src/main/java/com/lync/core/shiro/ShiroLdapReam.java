package com.lync.core.shiro;

import com.lync.common.vo.ShiroUser;
import com.lync.core.toolbox.kit.SpringKit;
import com.lync.domain.primary.User;
import com.lync.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.ldap.UnsupportedAuthenticationMechanismException;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/4/26 0026.
 */
public class ShiroLdapReam extends JndiLdapRealm {
    private static final Logger logger = LoggerFactory.getLogger(ShiroLdapReam.class);

    private String rootDN;
    public ShiroLdapReam() {
        super();
    }

    public String getRootDN() {
        return rootDN;
    }

    public void setRootDN(String rootDN) {
        this.rootDN = rootDN;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        IShiro shiroFactory = new LdapShiroFactory();
        ShiroUser shiroUser;
        try {
          queryForAuthenticationInfo(token, getContextFactory());
        } catch (AuthenticationNotSupportedException e) {
            String msg = "Unsupported configured authentication mechanism";
            throw new UnsupportedAuthenticationMechanismException(msg, e);
        } catch (javax.naming.AuthenticationException e) {
            String msg = "LDAP authentication failed.";
            throw new AuthenticationException(msg, e);
        } catch (NamingException e) {
            String msg = "LDAP naming error while attempting to authenticate user.";
            throw new AuthenticationException(msg, e);
        } catch (UnknownAccountException e) {
            String msg = "UnknownAccountException";
            throw new UnknownAccountException(msg, e);
        } catch (IncorrectCredentialsException e) {
            String msg = "IncorrectCredentialsException";
            throw new IncorrectCredentialsException(msg, e);
        }
        User user = shiroFactory.user(token.getPrincipal().toString());
        shiroUser= shiroFactory.shiroUser(user);
        return shiroFactory.info(shiroUser, user, getName());
    }


    @Override
    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, LdapContextFactory ldapContextFactory) throws NamingException {
        Object principal = token.getPrincipal( );
        Object credentials = token.getCredentials( );
        JndiLdapContextFactory ldapContextFactoryUpdate=null;
        User user;
        IShiro shiroFactory = new LdapShiroFactory();
        LdapContext systemCtx = null;
        LdapContext ctx = null;
        try {
            String ip = "";
            try {
                ip = InetAddress.getByName( "lenovo.com" ).getHostAddress( );
                System.out.println( "ip=" + ip );
                if(ip.equals( "10.38.0.111"))
                    logger.info( "这个是一个有问题的ip" );
            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.out.println( "zouwangbei" );
            }
            ldapContextFactoryUpdate = (JndiLdapContextFactory) ldapContextFactory;
            ldapContextFactoryUpdate.setUrl( "ldap://10.96.1.52:389" );

            systemCtx=ip.equals( "10.38.0.111" )?ldapContextFactoryUpdate.getSystemLdapContext( ):ldapContextFactory.getSystemLdapContext( );

            SearchControls constraints = new SearchControls( );
            constraints.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results = systemCtx.search( rootDN, "cn="
                    + principal, constraints );
            if (results != null && !results.hasMore( )) {
                throw new UnknownAccountException( );
            } else {
                boolean flag = true;
                while (results.hasMore( ) && flag) {
                    SearchResult si = (SearchResult) results.next( );
                    principal = si.getName( ) + "," + rootDN;
                    if (principal.toString( ).contains( "Accounts" )) {
                        flag = false;
                    }
                }
                logger.info( "DN=" + principal );
                try {
                    ctx=ip.equals( "10.38.0.111" )?ldapContextFactoryUpdate.getLdapContext( principal,credentials ):ldapContextFactory.getLdapContext( principal,
                            credentials );
                } catch (NamingException e) {
                    throw new IncorrectCredentialsException( );
                }
                UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
                UserService userService = (UserService) SpringKit.getBean( "userService" );
                user = userService.findByUsername(usertoken.getUsername());
                shiroFactory.shiroUser(user);
                return createAuthenticationInfo( token, principal, credentials,
                        ctx );
            }
        } finally {
            LdapUtils.closeContext( systemCtx );

            LdapUtils.closeContext( ctx );

        }
    }

}
