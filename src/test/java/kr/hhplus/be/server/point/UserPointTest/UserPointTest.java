package kr.hhplus.be.server.point.UserPointTest;

import kr.hhplus.be.server.domain.point.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserPointTest {

    @Test
    @DisplayName("[성공] 이미 포인트가 존재하는 경우, 포인트를 충전하면 성공")
    void givenUserPoint_whenChargePoint_thenPointIncrease_success() {
        // given
        UserPoint userPoint = new UserPoint(1, 1000);

        // when
        userPoint.charge(500);

        // then
        assertEquals(1500, userPoint.getPoint());
    }

    @Test
    @DisplayName("[실패] 포인트가 음수, 0인 경우 충전할때 IllegalArgumentException 에러 발생")
    void givenExsitingPoint_whenChargeZeroOrNegativePoint_thenFailure() {
        // given
        UserPoint userPoint = new UserPoint(1, 1000);

        // when & then
        IllegalArgumentException exception0 = assertThrows(IllegalArgumentException.class, () ->
            userPoint.charge(0)
        );
        assertEquals("충전 금액은 0보다 커야합니다.", exception0.getMessage());

        IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class, () ->
            userPoint.charge(-300)
        );
        assertEquals("충전 금액은 0보다 커야합니다.", exceptionNegative.getMessage());
    }

}