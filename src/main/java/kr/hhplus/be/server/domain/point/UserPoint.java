package kr.hhplus.be.server.domain.point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class UserPoint {

    private int userId;
    private int point;

    public void charge(int point) {
        if(point <= 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 커야합니다.");
        }
        this.point += point;
    }

}
