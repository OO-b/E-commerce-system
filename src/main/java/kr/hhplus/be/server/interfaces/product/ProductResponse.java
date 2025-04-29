package kr.hhplus.be.server.interfaces.product;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.product.ProductInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "전체 상품조회 Response")
public class ProductResponse {

        @Setter
        public static class AllProduct{
             @Schema(description = "상품 일련번호")
             private int productId;
             @Schema(description = "상품명")
             private String name;
             @Schema(description = "상품옵션")
             List<ProductResponse.ProductOption> options;
        }

        @Setter
        public static class ProductOption {
            @Schema(description = "상품옵션")
            private String optionName;
            @Schema(description = "가격")
            private int price;
        }

}