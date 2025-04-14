package kr.hhplus.be.server.infrastructure.point;

import kr.hhplus.be.server.domain.point.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPointJpaRepository extends JpaRepository<UserPoint, Integer> {
    Optional<UserPoint> findByUserId(int userId);
}