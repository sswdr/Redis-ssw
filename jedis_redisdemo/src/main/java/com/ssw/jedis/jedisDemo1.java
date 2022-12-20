package com.ssw.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Author: ssw
 * @DateTime: 2021/8/24 15:07
 * @Description: TODO
 */
public class jedisDemo1 {
    public static void main(String[] args) {
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.222.134", 6379);
        //测试
        String pong = jedis.ping();
        System.out.println("连接成功：" + pong);
        //jedis.close();
    }

    @Test
    public void test01() {
        Jedis jedis = new Jedis("192.168.222.134", 6379);
        jedis.mset("str1", "v1", "str2", "v2", "str3", "v3");
        System.out.println(jedis.mget("str1", "str2", "str3"));
    }

    @Test
    public void test02() {
        Jedis jedis = new Jedis("192.168.222.134", 6379);
        jedis.sadd("orders", "order01");
        jedis.sadd("orders", "order02");
        jedis.sadd("orders", "order03");
        jedis.sadd("orders", "order04");
        Set<String> smembers = jedis.smembers("orders");
        for (String order : smembers) {
            System.out.println(order);
        }
        jedis.srem("orders", "order02");
    }
}