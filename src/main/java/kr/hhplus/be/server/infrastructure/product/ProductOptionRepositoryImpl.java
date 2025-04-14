package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.ProductOption;
import kr.hhplus.be.server.domain.product.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductOptionRepositoryImpl implements ProductOptionRepository {

    @Override
    public List<ProductOption> findByProductId(int productId) {
        return null;
    }

    @Override
    public Optional<ProductOption> findByProductOptionId(int productOptionId) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductOption> findById(int optionId) {
        return Optional.empty();
    }

    @Override
    public List<ProductOption> findAll() {
        return null;
    }
}
