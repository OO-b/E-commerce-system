package kr.hhplus.be.server.domain.point;


import java.util.List;

public interface UserPointHistRepository {
    void save(UserPointHist userPointHist);
    List<UserPointHist> findByUserId(int userPointId);
}
