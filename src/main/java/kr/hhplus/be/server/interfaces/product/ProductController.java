package kr.hhplus.be.server.interfaces.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Product Controller", description = "Product Controller")
@RestController
@RequestMapping("/products")
public class ProductController implements ProductInterface {

    /**
     * 전체 상품 조회
     * */
    @Override
    @GetMapping
    public BaseResponse<List<AllProductResponse>> getAllProducts() {
        // Mock API 구현을 위해 임시로 만든 데이터
        List<AllProductResponse> allProductResponses = List.of(
                new AllProductResponse(1, "나이키 레드", 12000, 10),
                new AllProductResponse(2, "나이키 블랙", 8000, 20),
                new AllProductResponse(3, "아디다스 옐로우", 9000, 30),
                new AllProductResponse(4, "아디다스 블루", 3000, 40),
                new AllProductResponse(5, "아디다스 화이트", 5000, 50),
                new AllProductResponse(6, "아디다스 그린", 7000, 60)
        );

        return BaseResponse.of("0","Success", allProductResponses);
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
