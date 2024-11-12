package com.example.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository // @Repository: 데이터 저장소와 상호작용
public class CouponCountRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public CouponCountRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long increment() {
        return redisTemplate
                .opsForValue() // Redis의 문자열 값을 다루는 메소드
                .increment("coupon_count"); // Redis의 키 "coupon_count"의 값을 1 증가
    }


}
