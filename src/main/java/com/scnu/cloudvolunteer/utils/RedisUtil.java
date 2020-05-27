package com.scnu.cloudvolunteer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zzheng
 * @date 2020/05/27
 * @description：
 *  redis工具类
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /*-------------------------------------------------------------------------*/

    /**
     * string
     * 获取元素
     * @param key
     * @return
     */
    public String getValue(String key) { return (String)redisTemplate.opsForValue().get(key); }

    /**
     * string
     * 添加元素
     * @param key
     * @param value
     */
    public void setValue(String key, String value) { redisTemplate.opsForValue().set(key, value); }

    /**
     * string
     * 添加元素，并指定过期时间
     * @param key
     * @param value
     * @param l
     * @param timeUnit
     */
    public void setValue(String key, String value, Long l, TimeUnit timeUnit) { redisTemplate.opsForValue().set(key, value, l, timeUnit); }

    /**
     * list
     * 获取list的元素，指定获取第几个
     * @param key
     * @param index 第几个
     * @return
     */
    public Object getList(String key, Long index) { return redisTemplate.opsForList().index(key, index); }

    /**
     * list
     * 获取list的元素，指定范围
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List getList(String key, Long start, Long end) { return redisTemplate.opsForList().range(key, start, end); }

    /**
     * list
     * 添加list元素
     * 后面添加
     * @param key
     * @param value
     */
    public void setList(String key, Object value) { redisTemplate.opsForList().rightPush(key, value); }

    /**
     * list
     * 添加list
     * 添加一整个list,并设置过期时间
     * @param key
     * @param value
     * @param l
     * @param timeUnit
     */
    public void setList(String key, List value, long l, TimeUnit timeUnit) {
        redisTemplate.opsForList().rightPushAll(key, value);
        setExpire(key, l, timeUnit);
    }

    /**
     * list
     * 添加list
     * 添加一整个list
     * @param key
     * @param value
     */
    public void setList(String key, List value) { redisTemplate.opsForList().rightPushAll(key, value); }

    /**
     * list
     * 删除元素
     * @param key
     * @param count 删除个数
     * @param value 要删除的元素
     * @return
     */
    public Long delFromList(String key, long count, Object value){
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 获取指定元素的size
     * @param key
     * @return
     */
    public Long getSize(String key) { return redisTemplate.opsForList().size(key); }

    /**
     * hash
     * 获取map
     * @param key
     * @return
     */
    public Map getHash(String key) { return redisTemplate.opsForHash().entries(key); }

    /**
     * hash
     * 添加map
     * @param key redis的key
     * @param mapKey map的key
     * @param value map的value
     * @param <T>
     */
    public <T> void setHash(String key, String mapKey, T value){
        redisTemplate.opsForHash().put(key, mapKey, value);
    }

    /**
     * hash
     * 添加map
     * @param key
     * @param value
     */
    public void setHash(String key, Map value) { redisTemplate.opsForHash().putAll(key, value); }

    /**
     * hash
     * 添加map
     * 并指定过期时间
     * @param key
     * @param value
     * @param l
     * @param timeUnit
     */
    public void setHash(String key, Map value, long l, TimeUnit timeUnit) {
      redisTemplate.opsForHash().putAll(key, value);
      setExpire(key, l, timeUnit);
    }

    /**
     * 设置过期时间
     * @param key
     * @param l
     * @param timeUnit
     */
    public void setExpire(String key, long l, TimeUnit timeUnit) { redisTemplate.expire(key, l, timeUnit); }

    /**
     * 判断是否有该元素
     * @param key
     * @return
     */
    public boolean hasKey(String key) { return redisTemplate.hasKey(key).booleanValue(); }

    /**
     * 删除整个redis元素
     * @param key
     */
    public void del(String key) { redisTemplate.delete(key); }

    /**
     * 批量删除redis元素
     * @param mapkey
     * @param values
     */
    public void del(String mapkey, Object...values){
        redisTemplate.opsForHash().delete(mapkey, values);
    }
}
