package kr.hhplus.be.server.product;

import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductOption;
import kr.hhplus.be.server.domain.product.ProductOptionRepository;
import kr.hhplus.be.server.domain.product.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class ProductConcurrencyTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    private int testOptionId;

    @BeforeEach
    void setUp() {
        // 초기 재고 세팅
        ProductOption initialOption = new ProductOption(1,"아디다스 가젤", 3000, 5);
        ProductOption productOption = productOptionRepository.save(initialOption);
        testOptionId = productOption.getProductOptionId();
    }

    @Test
    void testtest() throws InterruptedException {
        int numberOfThreads = 5;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        int decreaseAmount = 1;

        for (int i = 0; i < numberOfThreads; i++) {
            List<ProductCommand.OrderOption> orderOptions = new ArrayList<>();

            executor.submit(() -> {
                try {
                    ProductCommand.OrderOption command = new ProductCommand.OrderOption(testOptionId, decreaseAmount);
                    orderOptions.add(command);
                    productService.decreaseProduct(ProductCommand.DecreaseStock.of(orderOptions));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        ProductOption option = productOptionRepository.findById(testOptionId)
                .orElseThrow(() -> new IllegalStateException("상품 옵션이 존재하지 않음"));

        executor.shutdown();

        // 검증 로직
        assertThat(option.getRemaining()).isEqualTo(0);
    }
}
