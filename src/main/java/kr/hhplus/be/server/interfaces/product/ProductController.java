package kr.hhplus.be.server.interfaces.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Product Controller", description = "Product Controller")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController implements ProductInterface {

    private final ProductService productService;

    /**
     * 전체 상품 조회
     * */
    @Override
    @GetMapping
    public BaseResponse<List<AllProductResponse>> getAllProducts() {

        List<Product> products = productService.getAllProducts();

        // Mock API 구현을 위해 임시로 만든 데이터
        List<AllProductResponse> allProductResponses = List.of(new AllProductResponse(1,
                "스페셜 커피",
                List.of(new AllProductResponse.Option("opt-001", "아메리카노", "3000", "25"),
                        new AllProductResponse.Option("opt-002", "라떼", "3500", "12"))
        ));

        return BaseResponse.of("0", "Success", allProductResponses);
    }


    /**
     * 상위 상품 조회
     * */
    @Override
    @GetMapping("/top")
    public BaseResponse<List<TopProductResponse>> getTopSellingProducts() {

        List<TopProductResponse> allProductResponses = List.of(
                new TopProductResponse(1,10,"나이키", 50000, 20),
                new TopProductResponse(2,20, "컨버스", 45000,10),
                new TopProductResponse(3,30,"뉴발란스", 20000, 5)
        );

        return BaseResponse.of("0","Success", allProductResponses);
    }
}
