package kr.hhplus.be.server.domain.product;

import io.micrometer.core.instrument.config.MeterFilter;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(int productId);
}
