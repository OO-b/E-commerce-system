package kr.hhplus.be.server.domain.product.ProductOption;

import java.util.List;

public interface ProductOptionRepository {
    List<ProductOption> findByProductId(int productId);

}
