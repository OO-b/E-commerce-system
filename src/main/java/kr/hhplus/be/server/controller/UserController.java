package kr.hhplus.be.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.controller.api.UserInterface;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Controller", description = "User Controller")
@RestController
@RequestMapping("/users")
public class UserController implements UserInterface {

    @Override
    @PostMapping("/{userId}/recharge")
    public void chargeUserPoint() {

    }

    @Override
    @GetMapping("/{userId}/recharge")
    public void getUserPoint() {

    }

    @Override
    @GetMapping("/{userId}/coupons")
    public void getUserCoupons() {

    }

    @Override
    @GetMapping("/{userId}/orders")
    public void orderProduct() {

    }

}
