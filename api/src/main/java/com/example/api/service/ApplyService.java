package com.example.api.service;

import com.example.api.domain.Coupon;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;

    public ApplyService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void apply(Long userId) {
        long count = couponRepository.count(); // 발급 쿠폰 카운트

        if (count > 100) { // 발급한 쿠폰 수가 100이상이면 리턴
            return;
        }

        couponRepository.save(new Coupon(userId)); // 100이하인 경우 쿠폰 발급

    }

}
