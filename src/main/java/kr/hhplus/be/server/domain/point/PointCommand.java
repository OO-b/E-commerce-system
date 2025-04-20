package kr.hhplus.be.server.domain.point;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PointCommand {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Usage {
        int userId;
        int totalPoint;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Charge {
        int userId;
        int point;
    }
}