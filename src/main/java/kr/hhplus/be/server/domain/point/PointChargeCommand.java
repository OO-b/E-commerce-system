package kr.hhplus.be.server.domain.point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PointChargeCommand {

    private int userId;
    private int point;

}
