package kr.hhplus.be.server.interfaces.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.product.ProductInfo;
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
    public BaseResponse<List<ProductResponse.AllProduct>> getAllProducts() {

        List<ProductInfo.ProductResult> products = productService.getAllProducts();

        List<ProductResponse.AllProduct> allProducts =  products.stream()
                .map(product -> {
                    ProductResponse.AllProduct response = new ProductResponse.AllProduct();
                    response.setProductId(product.getProductId());
                    response.setName(product.getName());

                    List<ProductResponse.ProductOption> optionResponses = product.getOptions().stream()
                            .map(option -> {
                                ProductResponse.ProductOption optionDto = new ProductResponse.ProductOption();
                                optionDto.setOptionName(option.getOptionName());
                                optionDto.setPrice(option.getPrice());
                                return optionDto;
                            })
                            .toList();

                    response.setOptions(optionResponses);
                    return response;
                })
                .toList();

        return BaseResponse.of("0","Success", allProducts);
    }

}
