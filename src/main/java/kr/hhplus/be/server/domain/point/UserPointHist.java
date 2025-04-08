package kr.hhplus.be.server.domain.point;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserPointHist {

    public enum Type {
        CHARGE, USE
    }

    private int userId;
    private int amount;
    private Type type;
    private LocalDateTime createdAt;

    public static UserPointHist of(int userId, int amount) {
        return new UserPointHist(userId, amount, Type.CHARGE, LocalDateTime.now());
    }
}
