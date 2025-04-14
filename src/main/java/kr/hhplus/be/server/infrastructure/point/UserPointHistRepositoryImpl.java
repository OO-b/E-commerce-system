package kr.hhplus.be.server.infrastructure.point;

import kr.hhplus.be.server.domain.point.UserPointHist;
import kr.hhplus.be.server.domain.point.UserPointHistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserPointHistRepositoryImpl implements UserPointHistRepository {

    private final UserPointHistJpaRepository userPointHistJpaRepository;

    @Override
    public void save(UserPointHist userPointHist) {
        userPointHistJpaRepository.save(userPointHist);
    }

    @Override
    public List<UserPointHist> findByUserId(int userId) {
        return userPointHistJpaRepository.findByUserId(userId);
    }

}