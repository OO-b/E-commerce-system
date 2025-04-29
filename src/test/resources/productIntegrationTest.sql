INSERT INTO product (product_id, name, created_at, updated_at)
VALUES
    (1, 'nike', NOW(), NOW()),
    (2, 'converse', NOW(), NOW());

INSERT INTO product_option (product_option_id, product_id, option_name, price, remaining, created_at, updated_at)
VALUES
    (1, 1, 'red 230', 100, 50, NOW(), NOW()),
    (2, 1, 'orange 240', 150, 30, NOW(), NOW()),
    (3, 2, 'yellow 250', 200, 40, NOW(), NOW());
