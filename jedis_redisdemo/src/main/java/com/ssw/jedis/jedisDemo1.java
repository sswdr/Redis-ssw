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
        Jedis jedis = new Jedis("ubuntu-20.04.wsl", 6379);
        //测试
        String pong = jedis.ping();
        System.out.println("连接成功：" + pong);
        //jedis.close();
    }

    @Test
    public void test01() {
        Jedis jedis = new Jedis("ubuntu-20.04.wsl", 6379);
        jedis.mset("str1", "v1", "str2", "v2", "str3", "v3");
        System.out.println(jedis.mget("str1", "str2", "str3"));
    }

    @Test
    public void test02() {
        Jedis jedis = new Jedis("ubuntu-20.04.wsl", 6379);
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

//package com.atguigu.jedis;
//
//import org.junit.Test;
//import redis.clients.jedis.Jedis;
//
//import java.util.List;
//import java.util.Set;
//
//public class JedisDemo1 {
//
//    public static void main(String[] args) {
//        //创建Jedis对象
//        Jedis jedis = new Jedis("ubuntu-20.04.wsl",6379);
//        //测试
//        String value = jedis.ping();
//        System.out.println(value);
//        jedis.close();
//    }
//
//    //操作zset
//    @Test
//    public void demo5() {
//        //创建Jedis对象
//        Jedis jedis = new Jedis("ubuntu-20.04.wsl",6379);
//
//        jedis.zadd("china",100d,"shanghai");
//
//        Set<String> china = jedis.zrange("china", 0, -1);
//        System.out.println(china);
//
//        jedis.close();
//    }
//
//    //操作hash
//    @Test
//    public void demo4() {
//        //创建Jedis对象
//        Jedis jedis = new Jedis("ubuntu-20.04.wsl",6379);
//
//        jedis.hset("users","age","20");
//        String hget = jedis.hget("users", "age");
//        System.out.println(hget);
//        jedis.close();
//    }
//
//    //操作set
//    @Test
//    public void demo3() {
//        //创建Jedis对象
//        Jedis jedis = new Jedis("ubuntu-20.04.wsl",6379);
//
//        jedis.sadd("names","lucy");
//        jedis.sadd("names","mary");
//
//        Set<String> names = jedis.smembers("names");
//        System.out.println(names);
//        jedis.close();
//    }
//
//    //操作list
//    @Test
//    public void demo2() {
//        //创建Jedis对象
//        Jedis jedis = new Jedis("ubuntu-20.04.wsl",6379);
//
//        jedis.lpush("key1","lucy","mary","jack");
//        List<String> values = jedis.lrange("key1", 0, -1);
//        System.out.println(values);
//        jedis.close();
//    }
//
//    //操作key string
//    @Test
//    public void demo1() {
//        //创建Jedis对象
//        Jedis jedis = new Jedis("ubuntu-20.04.wsl",6379);
//
//        //添加
//        jedis.set("name","lucy");
//
//        //获取
//        String name = jedis.get("name");
//        System.out.println(name);
//
//        //设置多个key-value
//        jedis.mset("k1","v1","k2","v2");
//        List<String> mget = jedis.mget("k1", "k2");
//        System.out.println(mget);
//
//        Set<String> keys = jedis.keys("*");
//        for(String key : keys) {
//            System.out.println(key);
//        }
//        jedis.close();
//    }
//}
