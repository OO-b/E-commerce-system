## 보유쿠폰 목록 조회
```mermaid
sequenceDiagram
actor A as client
participant B as couponController
participant C as couponService
participant D as couponRepository
A->>+B: 보유쿠폰조회 요청 
B->>+C: 보유쿠폰 조회
C->>+D: 사용자 보유쿠폰 조회
D-->>-C: 사용자 보유쿠폰 목록 반환
C-->>-B: 보유쿠폰반환
B-->>-A: 보유쿠폰 안내
```
