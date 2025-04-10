package kr.hhplus.be.server.domain.coupon;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UseCouponCommand {

    private int userId;
    private int couponId;

}
