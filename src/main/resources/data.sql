-- Insert Categories
INSERT INTO categories (id, name) VALUES (1, 'Món nướng');
INSERT INTO categories (id, name) VALUES (2, 'Món luộc');
INSERT INTO categories (id, name) VALUES (3, 'Món chay');
INSERT INTO categories (id, name) VALUES (4, 'Đồ uống');

-- Insert Sample Dishes
INSERT INTO dishes (id, name, description, image_url, price, start_date, last_modified_date, status, category_id) 
VALUES ('MN001', 'Sườn nướng BBQ', 'Sườn heo được tẩm ướp gia vị đặc biệt, nướng trên than hoa', 
        'https://example.com/images/suon-nuong.jpg', 150000.0, NOW(), NOW(), 'ON_SALE', 1);

INSERT INTO dishes (id, name, description, image_url, price, start_date, last_modified_date, status, category_id) 
VALUES ('MN002', 'Bò bít tết sốt tiêu xanh', 'Bò Mỹ nhập khẩu cao cấp với sốt tiêu xanh đặc trưng', 
        'https://example.com/images/bo-bit-tet.jpg', 250000.0, NOW(), NOW(), 'ON_SALE', 1);

INSERT INTO dishes (id, name, description, image_url, price, start_date, last_modified_date, status, category_id) 
VALUES ('MN003', 'Gà luộc nguyên con', 'Gà ta tươi luộc chín tới, ăn kèm xôi và nước mắm gừng', 
        'https://example.com/images/ga-luoc.jpg', 180000.0, NOW(), NOW(), 'ON_SALE', 2);

INSERT INTO dishes (id, name, description, image_url, price, start_date, last_modified_date, status, category_id) 
VALUES ('MN004', 'Rau củ luộc hỗn hợp', 'Rau củ tươi luộc sạch, giữ nguyên dinh dưỡng', 
        'https://example.com/images/rau-luoc.jpg', 50000.0, NOW(), NOW(), 'ON_SALE', 3);

INSERT INTO dishes (id, name, description, image_url, price, start_date, last_modified_date, status, category_id) 
VALUES ('MN005', 'Nước ép cam tươi', 'Cam tươi nguyên chất không đường', 
        'https://example.com/images/nuoc-ep-cam.jpg', 35000.0, NOW(), NOW(), 'ON_SALE', 4);

INSERT INTO dishes (id, name, description, image_url, price, start_date, last_modified_date, status, category_id) 
VALUES ('MN006', 'Cá nướng muối ớt', 'Cá tươi nướng với muối ớt thơm ngon', 
        'https://example.com/images/ca-nuong.jpg', 120000.0, NOW(), NOW(), 'STOPPED', 1);
