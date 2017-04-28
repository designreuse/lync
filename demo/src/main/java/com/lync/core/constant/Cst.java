package com.lync.core.constant;

import com.lync.core.shiro.DefaultShiroFactory;
import com.lync.core.shiro.IShiro;
import com.lync.core.shiro.LdapShiroFactory;
import com.lync.core.toolbox.cache.EhcacheFactory;
import com.lync.core.toolbox.cache.ICache;

/**
 * 系统配置类
 */
public class Cst {
    /**
     * 默认缓存工厂类
     */
    private ICache defaultCacheFactory = new EhcacheFactory();

    /**
     * 默认shirorealm工厂类
     */
    private  IShiro defaultShiroFactory=new DefaultShiroFactory();


    private static final Cst me = new Cst();
    public static Cst me() {
        return me;
    }

    public ICache getDefaultCacheFactory() {
        return defaultCacheFactory;
    }


    public IShiro getDefaultShiroFactory(){
        return  defaultShiroFactory;
    }


}
