package kr.hhplus.be.server.domain.point;

import java.util.Optional;

public interface UserPointRepository {

    Optional<UserPoint> findByUserId(int userId);
    void save(UserPoint userPoint);

}
