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

    private final ProductOptionJpaRepository productOptionJpaRepository;

    @Override
    public List<ProductOption> findByProductId(int productId) {
        return productOptionJpaRepository.findByProductId(productId);
    }

    @Override
    public Optional<ProductOption> findById(int optionId) {
        return productOptionJpaRepository.findById(optionId);
    }

    @Override
    public Optional<ProductOption> findByIdForUpdate(int optionId) {
        return productOptionJpaRepository.findByIdForUpdate(optionId);
    }

    @Override
    public ProductOption save(ProductOption option) {
        return productOptionJpaRepository.save(option);
    }

}
