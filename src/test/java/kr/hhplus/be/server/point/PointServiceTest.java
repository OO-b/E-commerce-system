package kr.hhplus.be.server.point;

import kr.hhplus.be.server.domain.point.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
        PointCommand.Charge command = new PointCommand.Charge(userId, chargeAmount);

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
        PointCommand.Charge command = new PointCommand.Charge(userId, point);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> pointService.charge(command));
    }


    @Test
    @DisplayName("[성공] 이미 사용자 포인트가 존재할때 포인트 사용시 포인트 차감되는 케이스")
    void givenExistingUserPoint_whenUsePoint_thenDecreasePoint_success() {
        // given
        int userId = 1;
        int currentPoint = 1000;
        int usagePoint = 300;

        UserPoint userPoint = new UserPoint(userId, currentPoint);
        PointCommand.Usage command = new PointCommand.Usage(userId, usagePoint);

        when(userPointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

        // when
        pointService.usePoints(command);

        // then
        assertEquals(700, userPoint.getPoint());
        ArgumentCaptor<UserPointHist> captor = ArgumentCaptor.forClass(UserPointHist.class);
        verify(userPointHistRepository).save(captor.capture());

        UserPointHist savedHist = captor.getValue();
        assertEquals(userId, savedHist.getUserId());
        assertEquals(-usagePoint, savedHist.getAmount());
        assertEquals(PointHistoryType.USE, savedHist.getType());
    }

    @Test
    @DisplayName("[실패] 사용하려는 포인트가 충전된 포인트보다 더 많을 때, IllegalStateException 에러발생")
    void givenNotEnoughPoint_whenUsePoint_thenFailure() {
        // given
        int userId = 1;
        int currentPoint = 100;
        int usagePoint = 500;

        UserPoint userPoint = new UserPoint(userId, currentPoint);
        PointCommand.Usage command = new PointCommand.Usage(userId, usagePoint);

        when(userPointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

        // expect
        assertThrows(IllegalStateException.class, () -> pointService.usePoints(command));
    }









}