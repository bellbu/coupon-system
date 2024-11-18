package com.example.consumer.consumer;

import com.example.consumer.domain.Coupon;
import com.example.consumer.domain.FailedEvent;
import com.example.consumer.repository.CouponRepository;
import com.example.consumer.repository.FailedEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;

    private final FailedEventRepository failedEventRepository;

    private final Logger logger = LoggerFactory.getLogger(CouponCreatedConsumer.class); // CouponCreatedConsumer 클래스에 대한 로거 인스턴스 생성

    public CouponCreatedConsumer(CouponRepository couponRepository, FailedEventRepository failedEventRepository) {
        this.couponRepository = couponRepository;
        this.failedEventRepository = failedEventRepository;
    }

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        // couponRepository.save(new Coupon(userId)); // 예외 처리 전
        try {
            couponRepository.save(new Coupon(userId));
        } catch (Exception e) { // 예외 처리 후
            logger.error("failed to create coupon::" + userId); // 쿠폰 발급 실패시 로그 기록
            failedEventRepository.save(new FailedEvent(userId)); // 쿠폰 발급 실패시 Id를 저장
        }

    }
}
