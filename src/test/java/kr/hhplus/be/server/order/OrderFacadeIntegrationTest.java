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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Slf4j
public class OrderFacadeIntegrationTest {

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
    void givenProductAndUserPoints_whenOrder_thenSuccess() {

        // given
        int userId = 1;
        int quantity = 2;

        OrderCriteria.OrderOption orderOption = new OrderCriteria.OrderOption(testOptionId, quantity);
        OrderCriteria.OrderProduct orderProduct = new OrderCriteria.OrderProduct(testProductId, List.of(orderOption));
        OrderCriteria criteria = new OrderCriteria(userId, null, List.of(orderProduct));  // 쿠폰 없이

        // when
        OrderResult result = orderFacade.order(criteria);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getOrderId()).isNotNull();
        assertThat(result.getTotalPrice()).isGreaterThan(0);
        assertThat(result.getTotalOrderCnt()).isEqualTo(1);
    }

}
