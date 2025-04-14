package kr.hhplus.be.server.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public List<ProductInfo.ProductResult> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> {
                    List<ProductInfo.ProductOptionResult> options =
                            productOptionRepository.findByProductId(product.getProductId()).stream()
                                    .map(option -> new ProductInfo.ProductOptionResult(
                                            option.getOptionNm(),
                                            option.getPrice()
                                    )).toList();
                        return new ProductInfo.ProductResult(
                                product.getProductId(),
                                product.getName(),
                                options
                        );
                }).toList();
    }


    /**
     * 재고차감 메소드
     * */
    public void decreaseProduct(List<ProductCommand.OrderOption> commands) {

        for (ProductCommand.OrderOption command : commands) {
            ProductOption option = productOptionRepository.findByProductOptionId(command.getProductOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("상품 옵션이 존재하지 않음"));

            option.decrease(command.getQuantity()); // 재고 차감
        }
    }


    public String getProductNameById(int productId) {
        return productRepository.findById(productId)
                .map(Product::getName)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다. id=" + productId));
    }

    public ProductOptionInfo getOptionInfoById(int optionId) {
        return productOptionRepository.findById(optionId)
                .map(option -> new ProductOptionInfo(option.getOptionNm(), option.getPrice()))
                .orElseThrow(() -> new NoSuchElementException("상품 옵션이 존재하지 않습니다. id=" + optionId));
    }
}
