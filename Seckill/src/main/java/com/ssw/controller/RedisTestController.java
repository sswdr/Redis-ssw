package com.ssw.controller;

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

/**
 * @author ssw
 * @date 2022/11/22 11:28
 */
@Api(tags = "redis测试模块")
@RestController
public class RedisTestController {

    @ApiImplicitParam(name = "prodId", value = "prodId", required = false)
    @ApiOperation(value = "秒杀案例")
    @GetMapping("/SecKill")
    public ResponseEntity<Boolean> getName(@RequestParam(value = "prodId") String prodId) throws Exception {
        String userId = new Random().nextInt(50000) + "";

        // boolean isSuccess = SecKill_redis.doSecKill(userId, prodId);
        boolean isSuccess = SecKill_redisByScript.doSecKill(userId, prodId);

        return ResponseEntity.ok(isSuccess);
    }
}
