package kr.hhplus.be.server.domain.product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

}
