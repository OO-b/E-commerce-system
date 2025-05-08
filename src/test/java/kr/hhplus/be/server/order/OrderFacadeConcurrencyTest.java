package kr.hhplus.be.server.order;

import kr.hhplus.be.server.application.order.OrderCriteria;
import kr.hhplus.be.server.application.order.OrderFacade;
import kr.hhplus.be.server.application.order.OrderResult;
import kr.hhplus.be.server.domain.point.UserPoint;
import kr.hhplus.be.server.domain.point.UserPointRepository;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductOption;
import kr.hhplus.be.server.domain.product.ProductOptionRepository;
import kr.hhplus.be.server.domain.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Slf4j
public class OrderFacadeConcurrencyTest {

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private UserPointRepository userPointRepository;

    private int testOptionId;
    private int testProductId;


    @BeforeEach
    void setUp() {
        // 초기 재고 세팅
        Product product = productRepository.save(new Product("아디다스"));
        ProductOption initialOption = new ProductOption(product.getProductId(),"아디다스 가젤", 3000, 5);
        ProductOption productOption = productOptionRepository.save(initialOption);
        testOptionId = productOption.getProductOptionId();
        testProductId = productOption.getProductId();
        //초기 사용자 포인트 세팅
        userPointRepository.save(new UserPoint(1,100000));
    }

    @Test
    @DisplayName("주문 파사드 동시성 테스트") // 분산락
    void orderFacadeConcurrencyTest() throws InterruptedException {
        int threadCount = 10;
        int userId = 1;
        int quantityPerOrder = 1;

        OrderCriteria.OrderOption orderOption = new OrderCriteria.OrderOption(testOptionId, quantityPerOrder);
        OrderCriteria.OrderProduct orderProduct = new OrderCriteria.OrderProduct(testProductId, List.of(orderOption));
        OrderCriteria criteria = new OrderCriteria(userId, null, List.of(orderProduct));  // 쿠폰 없음

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    orderFacade.order(criteria);
                } catch (Exception e) {
                    log.warn("주문 실패: {}", e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 쓰레드가 끝날 때까지 대기

        ProductOption option = productOptionRepository.findById(testOptionId).orElseThrow();
        log.info("남은 재고 수량: {}", option.getRemaining());

        assertThat(option.getRemaining()).isGreaterThanOrEqualTo(0);
        assertThat(option.getRemaining()).isLessThanOrEqualTo(5); // 성공 주문 수는 최대 5
    }

}
