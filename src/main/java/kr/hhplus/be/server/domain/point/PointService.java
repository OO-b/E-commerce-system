package kr.hhplus.be.server.domain.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserPointRepository userPointRepository;
    private final UserPointHistRepository userPointHistRepository;

    /**
     * 포인트 충전
     * */
    public UserPoint charge(PointChargeCommand command) {

        // 포인트 조회 (없는 경우 생성)
        UserPoint userPoint = userPointRepository.findByUserId(command.getUserId()).orElse(new UserPoint(command.getUserId(), 0));

        // 포인트 충전
        userPoint.charge(command.getPoint());

        // 내역 생성
        UserPointHist userPointHist = UserPointHist.of(
                userPoint.getUserId(),
                command.getPoint(),
                PointHistoryType.CHARGE);

        // 사용자 포인트 저장
        userPointRepository.save(userPoint);
        userPointHistRepository.save(userPointHist);
        return userPoint;

    }

    /**
     * 포인트 사용
     * */
    public void usePoints(PointUsageCommand pointUsageCommand) {
        UserPoint userPoint = userPointRepository.findByUserId(pointUsageCommand.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 포인트 정보가 없습니다."));

        if (userPoint.getPoint() < pointUsageCommand.getTotalPoint()) {
            throw new IllegalStateException("포인트가 부족합니다.");
        }

        userPoint.setPoint(userPoint.getPoint() - pointUsageCommand.getTotalPoint());
        userPointRepository.save(userPoint); // JPA 변경 감지라 생략 가능

        UserPointHist history = UserPointHist.of(
                pointUsageCommand.getUserId(),
                -pointUsageCommand.getTotalPoint(),
                PointHistoryType.USE
        );

        userPointHistRepository.save(history);
    }
}
