package kr.hhplus.be.server.point.pointServiceTest;

import kr.hhplus.be.server.domain.point.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    private UserPointHistRepository userPointHistRepository;

    @Mock
    private UserPointRepository userPointRepository;

    @InjectMocks
    private PointService pointService;

    @Test
    @DisplayName("[성공] 이미 사용자 포인트가 존재할때, 충전하는 케이스")
    void givenExistingUserPoint_whenCharge_thenPointIncreases_success() {
        // given
        int userId = 1;
        int chargeAmount = 1000;
        PointChargeCommand command = new PointChargeCommand(userId, chargeAmount);

        UserPoint existingPoint = new UserPoint(userId, 500); // 기존 포인트 500

        when(userPointRepository.findByUserId(userId)).thenReturn(Optional.of(existingPoint));

        // when
        pointService.charge(command);

        // then
        assertEquals(1500, existingPoint.getPoint());

        verify(userPointRepository).save(existingPoint);
        verify(userPointHistRepository).save(any(UserPointHist.class));
    }

    @Test
    @DisplayName("[실패] 포인트가 음수인 경우 충전할때, IllegalArgumentException 에러 발생")
    void givenNegativePoint_whenCharge_thenChargeFailure() {
        // given
        int userId = 1;
        int point = -500;
        PointChargeCommand command = new PointChargeCommand(userId, point);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> pointService.charge(command));
    }


}