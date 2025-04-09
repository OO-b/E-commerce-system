package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.domain.product.ProductOption.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
