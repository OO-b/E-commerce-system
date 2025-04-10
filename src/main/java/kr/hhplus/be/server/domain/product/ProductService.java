package kr.hhplus.be.server.domain.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new Product(
                        product.getProductId(),
                        product.getName(),
                        productOptionRepository.findByProductId(product.getProductId())
                ))
                .collect(Collectors.toList());
    }


    /**
     * 재고차감 메소드
     * */
    @Transactional
    public void decreaseProduct(List<OrderOptionCommand> commands) {

        for (OrderOptionCommand command : commands) {
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
