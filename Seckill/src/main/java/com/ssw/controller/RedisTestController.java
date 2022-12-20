package com.ssw.controller;

import cn.hutool.json.JSONObject;
import com.ssw.redis.SecKill_redis;
import com.ssw.redis.SecKill_redisByScript;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author ssw
 * @date 2022/11/22 11:28
 */
@Api(tags = "redis测试模块")
@RestController
public class RedisTestController {

    @ApiImplicitParam(name = "prodId", value = "prodId", required = false)
    @ApiOperation(value = "秒杀案例")
    @GetMapping("/SecKill1")
    public ResponseEntity<Boolean> SecKill_redisByScript_doSecKill(@RequestParam(value = "prodId") String prodId) throws Exception {
        String userId = new Random().nextInt(50000) + "";

        // boolean isSuccess = SecKill_redis.doSecKill(userId, prodId);
        boolean isSuccess = SecKill_redisByScript.doSecKill(userId, prodId);

        return ResponseEntity.ok(isSuccess);
    }

    @ApiImplicitParam(name = "商品prodId", value = "prodId", required = false)
    @ApiImplicitParam(name = "并发次数", value = "count", required = false)
    @ApiImplicitParam(name = "线程休眠时间", value = "threadSleep", required = false)
    @ApiOperation(value = "并发秒杀案例")
    @GetMapping("/SecKill2")
    public ResponseEntity<Boolean> SecKill_redis_doSecKill(@RequestParam(value = "prodId") String prodId,
                                                           @RequestParam(value = "prodId") Integer count,
                                                           @RequestParam(value = "threadSleep") Integer threadSleep) throws Exception {
        // 模拟5个并发请求
        CountDownLatch latch = new CountDownLatch(count);
        for (Integer i = 1; i <= count; i++) {
            Long id = i.longValue();
            new Thread(() -> {
                try {
                    // 先阻塞改线程
                    latch.await();
                    try {
                        String userId = new Random().nextInt(50000) + "";
                        boolean isSuccess = SecKill_redis.doSecKill(userId, prodId);
                        // boolean isSuccess = SecKill_redisByScript.doSecKill(userId, prodId);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }).start();
            try {
                Thread.sleep(10);
                // 让计数器-1，直至为0，唤醒所有被CountDownLatch阻塞的线程
                latch.countDown();
                System.out.println("countDownLatch执行一次，剩余count：" + latch.getCount());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        return ResponseEntity.ok(true);
    }
}
