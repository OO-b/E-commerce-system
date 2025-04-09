package kr.hhplus.be.server.product;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductOption.ProductOption;
import kr.hhplus.be.server.domain.product.ProductOption.ProductOptionRepository;
import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.domain.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductOptionRepository productOptionRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("[성공] 상품이 있을때, 전체상품 조회시 모든 상품 조회되는 케이스")
    void givenExistingProduct_whenFindAllProducts_thenGetAll_success(){

        // given
        Product nike = new Product(1, "나이키", null);
        Product adidas = new Product(2, "아디다스", null);

        when(productRepository.findAll()).thenReturn(List.of(nike,adidas));
        when(productOptionRepository.findByProductId(1)).thenReturn(
                List.of(new ProductOption(1,"맨투맨 블랙", 30000, 100),
                        new ProductOption(2,"맨투맨 레드", 20000, 100)
                )
        );
        when(productOptionRepository.findByProductId(2)).thenReturn(
                List.of(new ProductOption(2,"후디 화이트", 50000, 100),
                        new ProductOption(2,"후디 엘로우", 40000, 100)
                )
        );

        //when
        List<Product> result = productService.getAllProducts();

        System.out.println(result.get(0));
        System.out.println(result.get(1));

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getOptions()).hasSize(2);
        assertThat(result.get(1).getOptions()).hasSize(2);

        verify(productRepository).findAll();
        verify(productOptionRepository).findByProductId(1);
        verify(productOptionRepository).findByProductId(2);

    }

}