/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lync.core.toolbox.cache;


import java.util.List;

/**
 * Redis缓存工厂
 */
public class RedisCacheFactory extends BaseCacheFactory {


	@Override
	public void put(String cacheName, Object key, Object value) {

	}

	@Override
	public <T> T get(String cacheName, Object key) {
		return null;
	}

	@Override
	public List getKeys(String cacheName) {
		return null;
	}

	@Override
	public void remove(String cacheName, Object key) {

	}

	@Override
	public void removeAll(String cacheName) {

	}
}
