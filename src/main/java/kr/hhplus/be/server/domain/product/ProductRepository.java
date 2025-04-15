package kr.hhplus.be.server.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();
    Optional<Product> findById(int productId);
    void save(Product product);
}
