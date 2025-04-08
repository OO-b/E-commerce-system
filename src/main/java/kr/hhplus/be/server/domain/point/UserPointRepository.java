package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.domain.point.UserPoint;

import java.util.Optional;

public interface UserPointRepository {

    Optional<UserPoint> findByUserId(int userId);
    void save(UserPoint userPoint);

}
