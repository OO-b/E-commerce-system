package kr.hhplus.be.server.point;

import kr.hhplus.be.server.domain.point.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PointServiceIntegrationTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private UserPointHistRepository userPointHistRepository;

    @Test
    @DisplayName("[성공] 포인트 충전시, 사용자 포인트 및 내역 저장 성공")
    void whenChargePoint_thenStorageSuccess () {
        // given
        int userId = 1;
        int chargeAmount = 1000;
        PointCommand.Charge command = new PointCommand.Charge(userId, chargeAmount);

        // when
        pointService.charge(command);

        // then
        // 사용자 포인트 저장 확인
        Optional<UserPoint> userPointOpt = userPointRepository.findByUserId(userId);
        assertTrue(userPointOpt.isPresent());
        assertEquals(chargeAmount, userPointOpt.get().getPoint());

        // 포인트 내역 저장 확인
        List<UserPointHist> historyList = userPointHistRepository.findByUserId(userId);
        assertEquals(1, historyList.size());
        assertEquals(chargeAmount, historyList.get(0).getPoint());
        assertEquals(PointHistoryType.CHARGE, historyList.get(0).getType());
    }


    @Test
    @DisplayName("[성공] 포인트가 존재하는경우 사용자 포인트 조회 성공")
    void givenUserPoint_whenCheckPoint_thenSuccess() {
        // given
        int userId = 1;
        UserPoint saved = new UserPoint(userId, 100);
        userPointRepository.save(saved);

        // when
        UserPoint result = pointService.getPoint(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getPoint()).isEqualTo(100);
    }

    @Test
    @DisplayName("[실패] 포인트가 없는경우, RuntimeException 발생")
    void givenNoUserPoint_whenCheckPoint_thenFailure() {
        // given
        int nonExistentUserId = 999;

        // when & then
        assertThatThrownBy(() -> pointService.getPoint(nonExistentUserId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("포인트 정보가 없습니다.");
    }
}
