package kr.hhplus.be.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.controller.api.CouponInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Coupon Controller", description = "Coupon Controller")
@RestController
@RequestMapping("/coupon")
public class CouponController implements CouponInterface {

    @Override
    @GetMapping("/issue")
    public void issueFirstComeCoupon() {

    }
}
