package com.lync.config;

import com.lync.core.shiro.ShiroAuthorRealm;
import com.lync.core.shiro.ShiroDbRealm;
import com.lync.core.shiro.ShiroLdapReam;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.*;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.AbstractRememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.*;

/**
 * Created by breeze on 2017/2/25.
 */
@Configuration
public class ShiroConfig {

    public List<Realm> realms(){
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add( getShiroLdapReam());
        realms.add( getShiroAuthorRealm());
        return realms;
    }

//    @Bean("authenticator")
//    public Authenticator authenticator(){
//        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
//        authenticator.setAuthenticationStrategy(authenticationStrategy());
//        authenticator.setRealms(realms());
//        return  authenticator;
//    }

    /*只要有一个Realm验证成功即可，和FirstSuccessfulStrategy不同，返回所有Realm身份验证成功的认证信息；*/
//    @Bean("atLeastOneSuccessfulStrategy")
//    public AuthenticationStrategy authenticationStrategy(){
//        AtLeastOneSuccessfulStrategy atLeastOneSuccessfulStrategy = new AtLeastOneSuccessfulStrategy();
//        return atLeastOneSuccessfulStrategy;
//    }

    /*安全管理器*/
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("cacheManager")EhCacheManager  cacheManager,@Qualifier("rememberMeManager") AbstractRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealms( realms());
//        dwsm.setAuthenticator(authenticator());
        dwsm.setCacheManager(cacheManager);
        dwsm.setRememberMeManager(rememberMeManager);
        return dwsm;
    }


   /* 项目自定义的Realm*/
    @Bean(name = "shiroDbRealm")
    public ShiroDbRealm shiroDbRealm(@Qualifier("cacheManager") CacheManager cacheManager){
        ShiroDbRealm shiroDbRealm = new ShiroDbRealm( );
//         设置缓存管理器
        shiroDbRealm.setCacheManager(cacheManager);
//     启用身份验证缓存，即缓存AuthenticationInfo信息,默认false
        shiroDbRealm.setAuthorizationCachingEnabled( true );
//        缓存AuthenticationInfo信息的缓存名称
        shiroDbRealm.setAuthenticationCacheName( "authenticationCache" );
//        缓存AuthorizationInfo信息的缓存名称
        shiroDbRealm.setAuthorizationCacheName( "authorizationCache" );
        return shiroDbRealm;
    }



    @Bean(name = "shiroLdapReam")
    public ShiroLdapReam getShiroLdapReam() {
        ShiroLdapReam shiroLdapReam = new ShiroLdapReam();
        shiroLdapReam.setContextFactory(getJndiLdapContextFactory());
        shiroLdapReam.setRootDN("OU=User Accounts,DC=lenovo,DC=com");
        return shiroLdapReam;
    }


    @Bean(name = "shiroAuthorRealm")
    public ShiroAuthorRealm getShiroAuthorRealm() {
        ShiroAuthorRealm shiroAuthorRealm = new ShiroAuthorRealm();
        return shiroAuthorRealm;
    }

    @Bean(name = "contextFactory")
    public JndiLdapContextFactory getJndiLdapContextFactory(){
        JndiLdapContextFactory jndiLdapContextFactory=new JndiLdapContextFactory();
        jndiLdapContextFactory.setUrl("ldap://lenovo.com:389");
        jndiLdapContextFactory.setSystemUsername("EMMAdmin");
        jndiLdapContextFactory.setSystemPassword("abcd-1234");
        return  jndiLdapContextFactory;
    }



//    用户授权/认证信息CacheCache, 采用EhCache
    @Bean(name = "cacheManager")
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache.xml");
        return em;
    }
   /* rememberMe管理器*/
    @Bean("rememberMeManager")
    public AbstractRememberMeManager rememberMeManager(@Qualifier("rememberMeCookie") Cookie rememberMeCookie ){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager( );
        cookieRememberMeManager.setCookie(rememberMeCookie);
        cookieRememberMeManager.setCipherKey( org.apache.shiro.codec.Base64.decode( "U3ByaW5nQmxhZGUAAAAAAA==" ));
        return cookieRememberMeManager;
    }


    @Bean(name = "rememberMeCookie")
    public Cookie cookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe"); //在spring xml配置中构造注入是constructor-arg
        simpleCookie.setHttpOnly( true );
        simpleCookie.setMaxAge( 7*24*60*60 );  //7天，这种写法方便修改,spring的el表达式也是支持计算的,学习了
        return  simpleCookie;
    }
//    Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager

    /*启用shrio授权注解拦截方式*/
    @Bean
    public StaticMethodMatcherPointcutAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager")  DefaultWebSecurityManager defaultWebSecurityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor( );
        authorizationAttributeSourceAdvisor.setSecurityManager( defaultWebSecurityManager );
        return authorizationAttributeSourceAdvisor;
    }

    // 保证实现了Shiro内部lifecycle函数的bean执行
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    /*AOP式权限方法检查*/

    @DependsOn(value="lifecycleBeanPostProcessor")/*depend-on用来表示一个Bean的实例化依靠另一个Bean先实例化*/
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator( );
        defaultAdvisorAutoProxyCreator.setProxyTargetClass( true );
        return new DefaultAdvisorAutoProxyCreator();
    }


    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);/*安全管理器*/
        shiroFilterFactoryBean.setLoginUrl( "/login" );
//        shiroFilterFactoryBean.setSuccessUrl( "/index" );
//        shiroFilterFactoryBean.setUnauthorizedUrl( "/" );
        loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }


//    加载shiroFilter权限控制规则（从数据库读取然后配置）
private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){
    /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////

    Map<String, String> chains = new LinkedHashMap<String, String>();
//    chains.put("/logout", "logout");
//    chains.put("/logout", "logout");
//    chains.put("/assets/**", "anon");
//    chains.put("/blade/**", "anon");
//    chains.put("/jquery/**", "anon");
//    chains.put("/layer/**", "anon");
//    chains.put("/login/**", "anon");
//    chains.put("/my/**", "anon");

//    chains.put("/**", "authc");//所有路径需要认证
    chains.put("/admin/**", "roles[admin]");
    chains.put("/user/**", "authc");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);
}
// /admins/user/**=rest[user]


}
