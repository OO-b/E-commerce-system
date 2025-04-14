package kr.hhplus.be.server.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository {
    List<ProductOption> findByProductId(int productId);
    Optional<ProductOption> findByProductOptionId(int productOptionId);
    Optional<ProductOption> findById(int optionId);
    List<ProductOption> findAll();
}
