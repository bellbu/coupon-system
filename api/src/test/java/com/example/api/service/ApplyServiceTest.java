package com.example.api.service;

import com.example.api.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("docker")
@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void 한번만응모() {
        applyService.apply(1L); // 쿠폰 발급

        long count = couponRepository.count(); // 쿠폰 카운트

        assertThat(count).isEqualTo(1); // JUnit 테스트 // 한 개의 쿠폰 발급 검증
    }

    @Test
    public void 여러명응모() throws InterruptedException {

        // 스레드 설정 및 초기화
        int threadCount = 1000; // 1000개 스레드 선언
        ExecutorService executorService = Executors.newFixedThreadPool(32); // 32개 크기의 고정 스레드 풀 생성 ※ 스레드 풀: 미리 여러개의 스레드를 만들어 놓고 재사용
        CountDownLatch latch = new CountDownLatch(threadCount); // 1000개 스레드 대기: 스레드가 모두 실행을 완료할 때 까지 메인 스레드 대기

        // 동시 요청 시뮬레이션
        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            // 스레드 풀의 작업 큐 추가 ※ 큐 사이즈: 제한이 없음
            executorService.submit(() -> {
                try {
                    applyService.apply(userId); // 쿠폰 발급 메서드 호출
                } finally {
                    latch.countDown();  // 작업이 끝날 때마다 CountDownLatch의 카운트를 줄임
                }
            });
        }

        latch.await();  // 1000개 스레드가 작업을 완료할 때까지 메인 스레드를 대기 상태로 유지

        Thread.sleep(10000);

        long count = couponRepository.count();  // 발급된 쿠폰 수

        assertThat(count).isEqualTo(100); // 발급된 쿠폰 100개 검증
    }

}