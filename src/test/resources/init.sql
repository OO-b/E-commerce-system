CREATE TABLE user (
                      user_id INT PRIMARY KEY,          -- 사용자ID (PK)
                      user_nm VARCHAR(255),             -- 사용자명
                      created_at DATETIME,              -- 생성일시
                      updated_at DATETIME               -- 수정일시
);

CREATE TABLE user_point (
                            user_point_id INT PRIMARY KEY,     -- 사용자 포인트 ID (PK)
                            user_id INT,                       -- 사용자ID
                            point INT,                         -- 사용자 보유포인트
                            created_at DATETIME,               -- 생성일시
                            updated_at DATETIME                -- 수정일시
);

CREATE TABLE user_point_hist (
                                 user_point_id INT,                 -- 사용자 포인트 ID
                                 point INT,                         -- 포인트
                                 point_type VARCHAR(10),            -- 포인트타입 (충전/사용)
                                 created_at DATETIME                -- 생성일시
);

CREATE TABLE product (
                         product_id INT PRIMARY KEY,        -- 상품ID (PK)
                         name VARCHAR(50),                  -- 상품명
                         created_at DATETIME,               -- 생성일시
                         updated_at DATETIME                -- 수정일시
);

CREATE TABLE product_option (
                                product_option_id INT PRIMARY KEY, -- 상품옵션ID (PK)
                                product_id INT,                    -- 상품ID
                                option_nm VARCHAR(50),             -- 옵션명
                                price INT,                         -- 가격
                                remaining INT,                     -- 재고량
                                created_at DATETIME,               -- 생성일시
                                updated_at DATETIME                -- 수정일시
);

CREATE TABLE user_order (
                            order_id INT PRIMARY KEY,          -- 주문ID (PK)
                            customer_id INT,                   -- 주문자ID
                            status VARCHAR(50),                -- 상태
                            order_date DATETIME                -- 주문일시
);

CREATE TABLE order_item (
                            order_item_id INT PRIMARY KEY,     -- 주문상품ID (PK)
                            order_id INT,                      -- 주문ID
                            product_id INT,                    -- 상품ID
                            product_name VARCHAR(50),          -- 상품명
                            product_option_name VARCHAR(50),   -- 상품옵션명
                            product_price INT,                 -- 상품금액
                            product_amount INT                 -- 상품수량
);

CREATE TABLE payment (
                         payment_id INT PRIMARY KEY,        -- 결제ID (PK)
                         order_id INT,                      -- 주문ID
                         total_payment INT,                 -- 결제금액
                         payment_date DATETIME              -- 결제일시
);

CREATE TABLE coupon (
                        coupon_id INT PRIMARY KEY,         -- 쿠폰ID (PK)
                        coupon_nm VARCHAR(50),             -- 쿠폰명
                        discount_rate INT,                 -- 할인율
                        issued_count INT,                  -- 쿠폰 총 발급수량
                        remaining_count INT,               -- 쿠폰 잔여수량
                        issue_date DATETIME,               -- 쿠폰발행일
                        expiration_date DATETIME           -- 쿠폰만료일
);

CREATE TABLE user_coupon (
                             coupon_id INT,                     -- 쿠폰ID
                             user_id INT,                       -- 사용자ID
                             status CHAR(1),                    -- 쿠폰상태
                             issued_at DATETIME                 -- 발급일시
);