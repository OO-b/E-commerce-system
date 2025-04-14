package kr.hhplus.be.server.infrastructure.point;

import kr.hhplus.be.server.domain.point.UserPoint;
import kr.hhplus.be.server.domain.point.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPointRepositoryImpl implements UserPointRepository {

    private final UserPointJpaRepository userPointJpaRepository;

    @Override
    public Optional<UserPoint> findByUserId(int userId) {
        return userPointJpaRepository.findByUserId(userId);
    }

    @Override
    public void save(UserPoint userPoint) {
        userPointJpaRepository.save(userPoint);
    }
}