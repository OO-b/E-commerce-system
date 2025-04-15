package kr.hhplus.be.server.product;


import kr.hhplus.be.server.domain.product.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductIntegrationTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("[성공] 전체 상품 조회시, 상품정보 조회 성공")
    @Sql("/productIntegrationTest.sql")
    void givenProduct_whenGetAllProduct_thenSuccess() {

        // when
        List<ProductInfo.ProductResult> results = productService.getAllProducts();

        // then
        assertThat(results).hasSize(2);

        ProductInfo.ProductResult productA = results.stream()
                .filter(p -> p.getProductId() == 1)
                .findFirst()
                .orElseThrow();

        assertThat(productA.getName()).isEqualTo("nike");
        assertThat(productA.getOptions()).hasSize(2);
        assertThat(productA.getOptions()).extracting("optionNm")
                .containsExactlyInAnyOrder("red 230", "orange 240");
    }
}
