package kr.hhplus.be.server.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository {
    List<ProductOption> findByProductId(int productId);
    Optional<ProductOption> findById(int optionId);
    Optional<ProductOption> findByIdForUpdate(int optionId);
    ProductOption save(ProductOption option);
}
