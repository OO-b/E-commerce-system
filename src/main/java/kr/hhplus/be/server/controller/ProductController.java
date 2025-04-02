package kr.hhplus.be.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.controller.api.ProductInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product Controller", description = "Product Controller")
@RestController
@RequestMapping("/products")
public class ProductController implements ProductInterface {


    @Override
    @GetMapping("/")
    public void getAllProducts() {

    }

    @Override
    @GetMapping("/top")
    public void getTopSellingProducts() {

    }
}
