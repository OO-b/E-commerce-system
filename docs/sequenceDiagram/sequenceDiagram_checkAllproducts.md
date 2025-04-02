## 전체 상품 조회
```mermaid
sequenceDiagram
actor A as client
participant B as productController
participant C as productService
participant D as productRepository
A->>+B: 전체 상품 목록조회 요청 
B->>+C: 전체 상품 목록 조회
C->>+D: 전체 상품 목록 조회
D-->>-C: 전체 상품 목록 반환
C-->>-B: 전체 상품 목록 반환
B-->>-A: 전체 상품 목록 안내
```
