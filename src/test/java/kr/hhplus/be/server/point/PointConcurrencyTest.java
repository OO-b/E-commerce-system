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

    @Test
    @DisplayName("포인트 충전 - 동시성 테스트") // 낙관적락
    void givenUserPoint_whenSavingPointAtTheSameTime_thenFailure() throws InterruptedException {

        UserPoint setUserPoint = new UserPoint(1, 0);
        userPointRepository.save(setUserPoint);
        int userId = setUserPoint.getUserId();

        int threadCount = 5;
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

            int point = userPoint.get().getPoint(); // 사용자 잔액
            assertThat(histTotalPoint).isEqualTo(point); // 충전 후 포인트 잔액과 사용내역 합산내역 일치
            assertThat(point).isEqualTo(resultPoint); // 사용자 잔액과 성공 스레드 횟수 계산한 후 비교 일치
    }


    @Test
    @DisplayName("포인트 사용 - 동시성 테스트") // 낙관적락
    void givenUserPoint_whenUsingPointAtTheSameTime_thenFailure() throws InterruptedException {

        UserPoint setUserPoint = new UserPoint(1, 5000);
        userPointRepository.save(setUserPoint);
        int userId = setUserPoint.getUserId();

        int threadCount = 5;
        int usagePoint = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger();


        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    pointService.usePoints(new PointCommand.Usage(userId, usagePoint));
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

        int resultPoint = successCount.get() * usagePoint;

        Optional<UserPoint> userPoint = userPointRepository.findByUserId(userId);

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

        int expectedPoint = setUserPoint.getPoint() - resultPoint;

            int point = userPoint.get().getPoint();
            assertThat(histTotalPoint).isEqualTo(resultPoint);
            assertThat(point).isEqualTo(expectedPoint);

    }
}
