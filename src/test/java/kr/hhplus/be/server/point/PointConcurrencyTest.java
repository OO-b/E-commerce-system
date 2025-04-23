package kr.hhplus.be.server.point;


import kr.hhplus.be.server.domain.point.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
public class PointConcurrencyTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private UserPointHistRepository userPointHistRepository;

    private int userId;


    @BeforeEach
    void setUp() {
        UserPoint userPoint = new UserPoint(1, 0);
        userPointRepository.save(userPoint);
        userId = userPoint.getUserId();
    }


    @Test
    @DisplayName("포인트 충전 따닥이슈일때 - 동시성 테스트") // 낙관적락
    void givenAvailableCoupon_whenIssuingCouponAtTheSameTime_thenFailure() throws InterruptedException {

        int threadCount = 5; // 따닥으로 5회 충전 시도
        int chargePoint = 5000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    pointService.charge(new PointCommand.Charge(userId, chargePoint));
                    successCount.incrementAndGet(); // 성공한 충전만 카운트
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown(); // 스레드 풀 종료

        int resultPoint = successCount.get() * chargePoint;

        Optional<UserPoint> userPoint = userPointRepository.findByUserId(1);
        List<UserPointHist> userPointHists = userPointHistRepository.findByUserId(1);
        int histTotalPoint = userPointHists.stream()
                .mapToInt(hist -> {
                    if (hist.getType() == PointHistoryType.CHARGE) {
                        return hist.getPoint();
                    } else if (hist.getType() == PointHistoryType.USE) {
                        return -hist.getPoint();
                    } else {
                        return 0;
                    }
                })
                .sum();

        if(userPoint.isPresent()) {
            int point = userPoint.get().getPoint();
            assertThat(histTotalPoint).isEqualTo(point);
            assertThat(point).isEqualTo(resultPoint);
        }
    }
}
