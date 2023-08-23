package cn.objectspace.daomodule.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author Bill
 */
@Component
public class RedisOperateUtil<T> {
    final private RedisTemplate<String, T> redisTemplate;

    // 默认过期时间为1800秒
    static private final Duration TTL = Duration.ofSeconds(1800);

    @Autowired
    public RedisOperateUtil(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 插入新键值对
     * @param key String类型的键
     * @param value 泛型值
     * @return 插入结果
     */
    public boolean setKeyValue(String key, T value){
        try{
            redisTemplate.opsForValue().set(key, value, TTL);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    /**
     * 插入新键值对
     * @param key String类型的键
     * @param value 泛型值
     * @param ttl 过期时间
     * @return 插入结果
     */
    public boolean setKeyValue(String key, T value, long ttl){
        try{
            redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttl));
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    /**
     * 获取键值对
     * @param key 用于查询的String类型键
     * @return 查询的泛型值
     */
    public T getKeyValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除键值对
     * @param key 用于定位的String类型键
     * @return 删除结果
     */
    public boolean delKeyValue(String key){
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }
}
