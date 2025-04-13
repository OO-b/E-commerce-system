package kr.hhplus.be.server.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    public Coupon validateAndGetDiscount(CouponCommand.Usage command) {

        return couponRepository.findById(command.getCouponId())
                .filter(coupon -> !coupon.isExpired(LocalDateTime.now()))
                .flatMap(coupon ->
                        userCouponRepository.findByUserIdAndCouponId(command.getUserId(), command.getCouponId())
                                .filter(UserCoupon::isUsable)
                                .map(userCoupon -> coupon) // userCoupon이 조건 만족하면 coupon 리턴
                ).orElse(null); // 아니라면 null 처리
    }

    public void issueCoupon(CouponCommand.Issue command) {

        Coupon coupon = couponRepository.findById(command.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다."));

        // 쿠폰 유효성 및 재고 확인
        if (coupon.isExpired(LocalDateTime.now())) throw new IllegalStateException("쿠폰이 만료되었습니다.");

        // 중복 발급 체크
        boolean alreadyIssued = userCouponRepository.findByUserIdAndCouponId(command.getUserId(), command.getCouponId()).isPresent();
        if (alreadyIssued) throw new IllegalStateException("이미 발급된 쿠폰입니다.");

        coupon.issueOne(); // 재고 감소
        couponRepository.save(coupon);

        // 사용자 쿠폰 생성
        UserCoupon userCoupon = new UserCoupon(
                coupon.getCouponId(),
                command.getUserId(),
                CouponStatus.AVAILABLE,
                LocalDateTime.now()
        );

        userCouponRepository.save(userCoupon);

    }
}
