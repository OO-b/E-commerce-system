## 주문/결제 
```mermaid
sequenceDiagram
actor A as client
participant B as System
participant C as UserPoint
participant D as Product
participant E as Payment
participant F as UserCoupon

A->>+B: 상품 주문 요청
B->>+C: 충전포인트 확인
C-->>-B: 포인트 반환

alt 충전포인트 NOT EXIST
B-->>A: 주문실패
end

alt 충전포인트 EXIST
B->>+D: 상품구매 (갯수차감)
D-->>-B: 결과반환

alt 주문 가능개수 부족
B->>A:주문실패
end

alt 주문성공
B->>+E: 결제요청
opt 쿠폰 존재하는 case
E->>+F: 쿠폰 사용 가능여부 확인
F->>-E: 사용가능여부 반환
end
E-->>B: 결제결과 반환
B-->>-A: 상품 주문 결과 안내
end

end