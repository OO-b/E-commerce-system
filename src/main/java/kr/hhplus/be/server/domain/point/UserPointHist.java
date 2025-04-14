package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserPointHist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userPointId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int point;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointHistoryType type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserPointHist(int userId, int initialPoint, PointHistoryType type) {
        this.userId = userId;
        this.point = initialPoint;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }


    public static UserPointHist of(int userId, int point, PointHistoryType type) {
        return new UserPointHist(userId, point, type);
    }
}
