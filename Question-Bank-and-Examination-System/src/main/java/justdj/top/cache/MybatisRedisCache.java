/*
  Created by IntelliJ IDEA.
  User: 霜
  Date: 18.4.16
  Time: 12:29
*/

package justdj.top.cache;


import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class MybatisRedisCache implements Cache {
	
	public  String id;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
	
	//	通过setRedisTemplate注入
	private static RedisTemplate<String, Object> redisTemplate;
	
	public MybatisRedisCache(){
		logger.info("MybatisRedisCache无参构造函数初始化。");
	};
	
	public MybatisRedisCache(String cacheId) {
		logger.info("MybatisRedisCache有参构造函数初始化。");
		if (cacheId == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		this.id = cacheId;
		logger.info("缓存空间生成的名字Id:  " + this.id);
	}
	
	
	@Override
	public void putObject(Object key, Object value) {
		final String keyString = key.toString();
		final Object valuef = value;
		final long liveTime = 86400;
		logger.warn("加入缓存： key :" + keyString + " value :" + value);
		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyString.getBytes();
				byte[] valueb = SerializationUtils.serialize((Serializable) valuef);
				connection.set(keyb, valueb);
				if (liveTime > 0) {
					connection.expire(keyb, liveTime);
				}
				return 1L;
			}
		});
	}
	
	@Override
	public Object getObject(Object key) {
		final String keyf = key.toString();
		Object object ;
		object = redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] value = connection.get(keyf.getBytes());
				if (value == null) {
					logger.warn("缓存不存在!");
					return null;
				}
				logger.warn("缓存存在 :" +  SerializationUtils.deserialize(value));
				return SerializationUtils.deserialize(value);
			}
		});
		return  object;
	}
	
	@Override
	public Object removeObject(Object key) {
		final String keyf=key.toString();
		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				logger.warn("删除缓存 key :"+ keyf.getBytes());
				return connection.del(keyf.getBytes());
			}
		});
		return "success";
	}
	
	@Override
	public void clear() {
		redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				logger.warn("清空所有缓存");
				connection.flushDb();
				return "ok";
			}
		});
	}
	
	@Override
	public int getSize() {
		System.out.println("-------获取value大小------");
		redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.hGetAll(id.getBytes()).size();
				return "ok";
			}
		});
		return 0;
	}
	
	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}
	
	public RedisTemplate <String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate <String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public  void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
}

