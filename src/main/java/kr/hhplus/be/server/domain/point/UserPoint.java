package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userPointId;

    @Column(nullable = false, unique = true)
    private int userId;

    @Column(nullable = false)
    private int point;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserPoint(int userId, int initialPoint) {
        this.userId = userId;
        this.point = initialPoint;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void charge(int point) {
        if(point <= 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 커야합니다.");
        }
        this.point += point;
    }

}
