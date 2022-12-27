package com.ssw.controller;

import com.ssw.redis.SecKill_redis;
import com.ssw.redis.SecKill_redisByScript;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiOperation(value = "秒杀案例")
    @GetMapping("/SecKill1")
    @ApiImplicitParam(name = "prodId", value = "商品prodId", required = false)
    public ResponseEntity<Boolean> SecKill_redisByScript_doSecKill(@RequestParam(value = "prodId") String prodId) throws Exception {
        String userId = new Random().nextInt(50000) + "";

        // boolean isSuccess = SecKill_redis.doSecKill(userId, prodId);
        boolean isSuccess = SecKill_redisByScript.doSecKill(userId, prodId);

        return ResponseEntity.ok(isSuccess);
    }

    /**
     * prodId       商品prodId
     * count        并发次数
     * threadSleep  线程休眠时间
     */
    @ApiOperation(value = "并发秒杀案例")
    @GetMapping("/SecKill2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prodId", value = "商品prodId", dataType = "string", paramType = "query", example = "1", required = false),
            @ApiImplicitParam(name = "count", value = "并发次数", dataType = "int", paramType = "query", example = "100", required = false),
            @ApiImplicitParam(name = "threadSleep", value = "线程休眠时间", dataType = "int", paramType = "query", example = "10", required = false),
    })
    public ResponseEntity<Boolean> SecKill_redis_doSecKill(@RequestParam(value = "prodId") String prodId,
                                                           @RequestParam(value = "count") Integer count,
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
                Thread.sleep(threadSleep);
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
