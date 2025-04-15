package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOption, Integer> {

    List<ProductOption> findByProductId(int productId);
}
