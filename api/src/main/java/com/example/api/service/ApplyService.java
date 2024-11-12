package com.example.api.service;

import com.example.api.domain.Coupon;
import com.example.api.producer.CouponCreateProducer;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    private final CouponCreateProducer couponCreateProducer;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
    }

    public void apply(Long userId) {
        // long count = couponRepository.count(); //  쿠폰 발급 카운트 but 경쟁조건(race condition) 문제 발생
        Long count = couponCountRepository.increment();  // Redisd의 싱글 스레드를 활용하여 쿠폰 발급 카운트 경쟁조건 문제 해결 ※ 서버 1대일때 자바 Synchronized 사용하여 문제해결 가능

        if (count > 100) { // 발급한 쿠폰 수가 100이상이면 리턴
            return;
        }

        // couponRepository.save(new Coupon(userId)); // 100이하인 경우 쿠폰 발급 but 발급할 쿠폰 수가 많아질수록 DB 부하 문제 발생
        couponCreateProducer.create(userId); // "coupon create"이라는 Kafka 토픽에 userId 값을 메시지로 전송
    }

}
