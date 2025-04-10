package kr.hhplus.be.server.domain.point;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserPointHist {


    private int userId;
    private int amount;
    @Enumerated(EnumType.STRING)
    private PointHistoryType type;
    private LocalDateTime createdAt;

    public static UserPointHist of(int userId, int amount, PointHistoryType type) {
        return new UserPointHist(userId, amount, type, LocalDateTime.now());
    }
}
