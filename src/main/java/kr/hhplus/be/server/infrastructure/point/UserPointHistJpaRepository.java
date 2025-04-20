package kr.hhplus.be.server.infrastructure.point;

import kr.hhplus.be.server.domain.point.UserPointHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointHistJpaRepository extends JpaRepository<UserPointHist, Integer> {
    List<UserPointHist> findByUserId(int userId);
}