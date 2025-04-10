package kr.hhplus.be.server.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderProductCommand {
    private int productId;
    private String productName;
    private String optionName;
    private int productPrice;
    private int quantity;
}