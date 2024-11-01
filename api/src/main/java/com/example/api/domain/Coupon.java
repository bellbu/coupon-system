package com.example.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // @Entity: 스프링의 객체와 DB의 테이블을 매핑
public class Coupon {

    @Id // 해당 필드를 pk로 간주
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 자동 생성: strategy = GenerationType.IDENTITY(MySQL의 auto_increment와 동일)
    private Long id;

    private Long userId;

    public Coupon() {
    }

    public Coupon(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

}
