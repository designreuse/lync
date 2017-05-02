package com.lync.core.toolbox.cache;

/**
 * Created by Administrator on 2017/4/19 0019.
 */

import java.util.List;

/**
 * 通用缓存接口
 */
public interface ICache {


    public void put(String cacheName, Object key, Object value);

    public <T> T get(String cacheName, Object key);

    public List getKeys(String cacheName);

    public void remove(String cacheName, Object key);

    public void removeAll(String cacheName);

    public <T> T get(String cacheName, Object key, ILoader iLoader);

    public <T> T get(String cacheName, Object key, Class<? extends ILoader> iLoaderClass);

}
