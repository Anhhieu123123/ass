# Restaurant Management API

API quản lý món ăn và danh mục cho nhà hàng sử dụng Spring Boot.

## 🚀 Tính năng

- ✅ Quản lý danh mục món ăn (Category)
- ✅ Quản lý món ăn (Dish) với CRUD đầy đủ
- ✅ Phân trang, lọc và sắp xếp
- ✅ Validation dữ liệu đầu vào
- ✅ Soft delete (xóa mềm)
- ✅ API Response wrapper chuẩn
- ✅ Exception handling toàn cục
- ✅ Swagger/OpenAPI documentation

## 📋 Yêu cầu

- Java 17+
- Maven 3.6+
- MySQL 8.0+ (hoặc H2 Database cho testing)

## 🛠️ Cài đặt

### 1. Clone project

```bash
cd restaurant-api
```

### 2. Cấu hình database

Mở file `src/main/resources/application.properties` và cấu hình MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Build project

```bash
mvn clean install
```

### 4. Chạy ứng dụng

```bash
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

## 📚 API Documentation

Sau khi chạy ứng dụng, truy cập Swagger UI tại:

```
http://localhost:8080/swagger-ui.html
```

## 🔗 Danh sách API Endpoints

### Category APIs

#### 1. Lấy danh sách tất cả danh mục

```http
GET /api/v1/categories
```

**Response:**
```json
{
  "success": true,
  "message": "Success",
  "data": [
    { "id": 1, "name": "Món nướng" },
    { "id": 2, "name": "Món luộc" }
  ],
  "timestamp": "2025-10-23T10:00:00"
}
```

### Dish APIs

#### 2. Lấy danh sách món ăn (có phân trang và lọc)

```http
GET /api/v1/dishes?page=1&limit=5&sortBy=startDate&sortDir=desc&status=ON_SALE
```

**Query Parameters:**
- `page` (int, default: 1) - Số trang
- `limit` (int, default: 5) - Số lượng mỗi trang
- `sortBy` (string, default: startDate) - Trường sắp xếp (name, price, startDate)
- `sortDir` (string, default: desc) - Hướng sắp xếp (asc, desc)
- `status` (enum, default: ON_SALE) - Trạng thái (ON_SALE, STOPPED)
- `keyword` (string, optional) - Tìm kiếm theo tên hoặc mô tả
- `categoryId` (long, optional) - Lọc theo danh mục
- `minPrice` (double, optional) - Giá tối thiểu
- `maxPrice` (double, optional) - Giá tối đa

**Response:**
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "content": [
      {
        "id": "MN001",
        "name": "Sườn nướng BBQ",
        "description": "Sườn heo được tẩm ướp...",
        "imageUrl": "https://example.com/images/suon-nuong.jpg",
        "price": 150000.0,
        "startDate": "2025-10-23T10:00:00Z",
        "lastModifiedDate": "2025-10-23T10:00:00Z",
        "status": "ON_SALE",
        "category": {
          "id": 1,
          "name": "Món nướng"
        }
      }
    ],
    "page": 1,
    "limit": 5,
    "totalElements": 10,
    "totalPages": 2
  },
  "timestamp": "2025-10-23T10:00:00"
}
```

#### 3. Lấy chi tiết một món ăn

```http
GET /api/v1/dishes/{id}
```

**Response:**
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "id": "MN001",
    "name": "Sườn nướng BBQ",
    ...
  },
  "timestamp": "2025-10-23T10:00:00"
}
```

#### 4. Thêm món ăn mới

```http
POST /api/v1/dishes
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Bò bít tết sốt tiêu xanh",
  "description": "Bò Mỹ nhập khẩu cao cấp",
  "imageUrl": "https://example.com/images/bo-bit-tet.jpg",
  "price": 250000.0,
  "categoryId": 1
}
```

**Validation Rules:**
- `name`: Bắt buộc, độ dài > 7 ký tự
- `price`: Bắt buộc, giá trị > 0
- `categoryId`: Bắt buộc, phải tồn tại

**Cách Validation:**
Project sử dụng **Custom Validator Class** thay vì annotation `@Valid`:
- Class `DishValidator` chứa tất cả logic validation
- Validation được gọi trong Controller trước khi xử lý
- Lỗi validation trả về trong `ApiResponse` với format nhất quán

**Response (400 Bad Request) - Validation Error:**
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Tên món ăn phải dài hơn 7 ký tự",
    "price": "Giá món ăn phải lớn hơn 0"
  },
  "timestamp": "2025-10-23T10:00:00"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Tạo món ăn thành công",
  "data": {
    "id": "MN007",
    "name": "Bò bít tết sốt tiêu xanh",
    "status": "ON_SALE",
    "startDate": "2025-10-23T10:00:00Z",
    ...
  },
  "timestamp": "2025-10-23T10:00:00"
}
```

#### 5. Cập nhật món ăn

```http
PUT /api/v1/dishes/{id}
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Bò bít tết sốt tiêu đen",
  "description": "Bò Mỹ nhập khẩu cao cấp",
  "imageUrl": "https://example.com/images/bo-bit-tet-v2.jpg",
  "price": 260000.0,
  "categoryId": 1,
  "status": "STOPPED"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Cập nhật món ăn thành công",
  "data": {
    "id": "MN007",
    "name": "Bò bít tết sốt tiêu đen",
    "lastModifiedDate": "2025-10-23T11:00:00Z",
    ...
  },
  "timestamp": "2025-10-23T11:00:00"
}
```

#### 6. Xóa món ăn (Soft Delete)

```http
DELETE /api/v1/dishes/{id}
```

**Response (204 No Content):**
```json
{
  "success": true,
  "message": "Xóa món ăn thành công",
  "data": null,
  "timestamp": "2025-10-23T11:00:00"
}
```

## 🔒 Error Responses

### 400 Bad Request - Validation Error

```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Tên món ăn phải dài hơn 7 ký tự",
    "price": "Giá món ăn phải lớn hơn 0",
    "categoryId": "Không tìm thấy danh mục với ID: 999"
  },
  "timestamp": "2025-10-23T10:00:00"
}
```

**Note:** Validation errors được xử lý bởi Custom Validator Class và trả về trong format nhất quán với `ApiResponse`.

### 404 Not Found

```json
{
  "success": false,
  "message": "Không tìm thấy món ăn với ID: MN999",
  "data": null,
  "timestamp": "2025-10-23T10:00:00"
}
```

### 409 Conflict

```json
{
  "success": false,
  "message": "Món ăn đã bị xóa trước đó",
  "data": null,
  "timestamp": "2025-10-23T10:00:00"
}
```

## 📁 Cấu trúc Project

```
restaurant-api/
├── src/
│   ├── main/
│   │   ├── java/com/restaurant/api/
│   │   │   ├── config/          # Cấu hình (OpenAPI)
│   │   │   ├── controller/      # REST Controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA Entities
│   │   │   ├── exception/       # Custom Exceptions
│   │   │   ├── repository/      # JPA Repositories
│   │   │   ├── service/         # Business Logic
│   │   │   ├── validator/       # Custom Validators ⭐
│   │   │   └── RestaurantApiApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql         # Dữ liệu mẫu
│   └── test/
├── pom.xml
├── README.md
├── VALIDATION_EXAMPLES.md       # Chi tiết về validation ⭐
└── GRADING.md
```

## 🧪 Testing với Postman

Import collection từ Swagger hoặc sử dụng các endpoint trên.

### Test Flow mẫu:

1. Lấy danh sách categories: `GET /api/v1/categories`
2. Tạo món ăn mới: `POST /api/v1/dishes`
3. Lấy danh sách món ăn: `GET /api/v1/dishes?page=1&limit=5`
4. Cập nhật món ăn: `PUT /api/v1/dishes/MN001`
5. Xóa món ăn: `DELETE /api/v1/dishes/MN001`

## 📊 Database Schema

### Table: categories

| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| name | VARCHAR(100) | NOT NULL |

### Table: dishes

| Column | Type | Constraints |
|--------|------|-------------|
| id | VARCHAR(20) | PRIMARY KEY |
| name | VARCHAR(200) | NOT NULL |
| description | TEXT | |
| image_url | VARCHAR(500) | |
| price | DOUBLE | NOT NULL |
| start_date | DATETIME | NOT NULL |
| last_modified_date | DATETIME | NOT NULL |
| status | ENUM | NOT NULL (ON_SALE, STOPPED, DELETED) |
| category_id | BIGINT | FOREIGN KEY → categories(id) |

## 🎯 Business Rules

1. **Validation:**
   - Tên món ăn: bắt buộc, > 7 ký tự
   - Giá: bắt buộc, > 0
   - Category: phải tồn tại trong database

2. **Auto-generated:**
   - `id`: Tự động tạo (format: MNxxx)
   - `startDate`: Tự động gán khi tạo
   - `lastModifiedDate`: Tự động cập nhật khi sửa
   - `status`: Mặc định ON_SALE khi tạo

3. **Soft Delete:**
   - Món bị xóa chỉ chuyển status sang DELETED
   - Không hiển thị trong danh sách
   - Không cho phép sửa hoặc xóa lại

4. **Filter Rules:**
   - Mặc định chỉ hiển thị món ON_SALE
   - Món DELETED không bao giờ xuất hiện trong API list

## 🔧 Technologies

- Spring Boot 3.2.0
- Spring Data JPA
- MySQL Connector
- Hibernate
- Lombok
- Spring Validation (Custom Validator Pattern)
- Swagger/OpenAPI 3
- Maven

## 🎨 Custom Validation Pattern

Project này sử dụng **Custom Validator Class** thay vì Spring's `@Valid` annotation:

### Ưu điểm:
- ✅ Linh hoạt hơn - có thể thêm logic phức tạp (check database, external API)
- ✅ Kiểm soát tốt hơn - biết chính xác validation ở đâu
- ✅ Response format nhất quán - tất cả lỗi đều trong `ApiResponse`
- ✅ Dễ test - có thể test validator độc lập

### Cách sử dụng:
```java
// 1. Tạo ValidationResult
ValidationResult result = dishValidator.validateCreateDish(request);

// 2. Check errors
if (result.hasErrors()) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error("Validation failed", result.getErrors()));
}

// 3. Continue processing if valid
```

Xem chi tiết tại [VALIDATION_EXAMPLES.md](VALIDATION_EXAMPLES.md)

## 📝 License

Apache License 2.0

## 👥 Author

Restaurant Development Team

---

**Happy Coding! 🍽️**
