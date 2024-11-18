package com.example.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppliedUserRepository { // 해당 아이디 중복 쿠폰 발급 여부 확인을 위해 Redis의 Set을 관리하기 위한 리포지토리

    private final RedisTemplate<String, String> redisTemplate;

    public AppliedUserRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long add(Long userId) {  // Set유형에 쿠폰 발급된 userId를 넣기 위한 메소드
        return redisTemplate
                .opsForSet()
                .add("applied_user", userId.toString());
    }
}
