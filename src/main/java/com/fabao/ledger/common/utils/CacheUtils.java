package com.fabao.ledger.common.utils;




import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.fabaoframework.modules.springextend.SpringContextHolder;

/**
 * Cache工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CacheUtils {
	
	private static CacheManager cacheManager = ((CacheManager)SpringContextHolder.getBean("cacheManager"));

	private static final String SYS_CACHE = "sysCache";

	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}

	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}
	
	public static Object get(String cacheName, String key) {
		if(getCache(cacheName).get(key)!=null){
			return getCache(cacheName).get(key).get();
		}
		return null;
	}

	public static void put(String cacheName, String key, Object value) {
		getCache(cacheName).put(key,value);
	}

	public static void remove(String cacheName, String key) {
		getCache(cacheName).evict(key);
	}
	
	/**
	 * 获得一个Cache
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheKey){
		Cache cache = cacheManager.getCache(cacheKey);
		return cache;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
