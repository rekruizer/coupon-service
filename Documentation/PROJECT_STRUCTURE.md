# 📁 Структура проекта Coupon Service

## Общая структура

```
coupon-service/
├── .mvn/                           # Maven Wrapper файлы
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
│
├── src/                            # Исходный код
│   └── main/
│       ├── java/                   # Java код
│       │   └── com/ecommerce/coupon/
│       │       ├── CouponServiceApplication.java
│       │       ├── controller/
│       │       ├── service/
│       │       ├── repository/
│       │       ├── model/
│       │       ├── dto/
│       │       ├── exception/
│       │       └── config/
│       │
│       └── resources/              # Ресурсы
│           ├── application.properties
│           └── static/
│               └── index.html
│
├── target/                         # Скомпилированные файлы (генерируется)
│
├── mvnw                            # Maven Wrapper (Mac/Linux)
├── mvnw.cmd                        # Maven Wrapper (Windows)
├── pom.xml                         # Maven конфигурация
│
├── README.md                       # Основная документация
├── WINDOWS_SETUP.md                # Инструкция для Windows
├── QUICKSTART.md                   # Быстрый старт
├── PLAN.md                         # План разработки
└── .gitignore                      # Git ignore файлы
```

---

## 📦 Детальная структура Java кода

### 1️⃣ Главный класс

```
CouponServiceApplication.java
└── Точка входа в приложение
    └── Метод main() запускает Spring Boot
```

### 2️⃣ Controller (REST API)

```
controller/
└── CouponController.java
    ├── POST   /api/coupons              (создать купон)
    ├── GET    /api/coupons              (список купонов)
    ├── GET    /api/coupons/{code}       (купон по коду)
    ├── POST   /api/coupons/{code}/apply (применить купон)
    └── DELETE /api/coupons/{code}       (удалить купон)
```

### 3️⃣ Service (Бизнес-логика)

```
service/
└── CouponService.java
    ├── generateCode()           (генерация уникального кода)
    ├── createCoupon()           (создание купона)
    ├── getAllCoupons()          (получить все купоны)
    ├── getCouponByCode()        (найти по коду)
    ├── validateAndApply()       (валидация + применение)
    ├── calculateDiscount()      (расчет скидки)
    └── deleteCoupon()           (удаление купона)
```

### 4️⃣ Repository (Работа с БД)

```
repository/
└── CouponRepository.java
    ├── findByCode(String code)       (найти по коду)
    ├── existsByCode(String code)     (проверить существование)
    └── [все стандартные JPA методы]  (save, findAll, delete, и т.д.)
```

### 5️⃣ Model (Сущности)

```
model/
├── Coupon.java              (Entity - таблица в БД)
│   ├── id
│   ├── code
│   ├── discountType
│   ├── discountValue
│   ├── minOrderAmount
│   ├── expiryDate
│   ├── usageLimit
│   ├── usageCount
│   ├── active
│   └── createdAt
│
└── DiscountType.java        (Enum)
    ├── PERCENTAGE           (процентная скидка)
    ├── FIXED_AMOUNT         (фиксированная сумма)
    └── FREE_SHIPPING        (бесплатная доставка)
```

### 6️⃣ DTO (Data Transfer Objects)

```
dto/
├── CouponCreateRequest.java      (входные данные для создания)
├── CouponResponse.java           (данные для ответа API)
├── ValidationRequest.java        (данные для валидации)
└── ValidationResponse.java       (результат валидации)
```

### 7️⃣ Exception (Обработка ошибок)

```
exception/
├── CouponNotFoundException.java    (купон не найден)
├── CouponExpiredException.java     (купон истек)
├── InvalidCouponException.java     (невалидный купон)
└── GlobalExceptionHandler.java     (глобальный обработчик)
```

### 8️⃣ Config (Конфигурация)

```
config/
└── WebConfig.java
    └── CORS настройки (для работы frontend)
```

---

## 🌐 Frontend структура

```
resources/static/
└── index.html                    (Single Page Application)
    ├── Header                    (заголовок)
    ├── Section 1: Создание купона
    ├── Section 2: Список купонов (таблица)
    ├── Section 3: Применение купона
    └── Footer
```

**Технологии:**
- HTML5
- Tailwind CSS (CDN)
- Alpine.js (CDN)
- Vanilla JavaScript

---

## ⚙️ Конфигурационные файлы

### pom.xml (Maven)
```xml
Зависимости:
├── Spring Boot Starter Web
├── Spring Boot Starter Data JPA
├── H2 Database
├── Lombok
└── Spring Boot Starter Validation
```

### application.properties
```properties
Настройки:
├── Порт сервера: 8080
├── База данных: H2 (in-memory)
├── JPA: hibernate ddl-auto=create-drop
└── Логирование: DEBUG
```

---

## 🗂️ Maven Wrapper файлы

```
.mvn/wrapper/
├── maven-wrapper.jar         (JAR для загрузки Maven)
└── maven-wrapper.properties  (URL для скачивания Maven)

mvnw                          (скрипт для Mac/Linux)
mvnw.cmd                      (скрипт для Windows)
```

**Назначение:** Позволяет запускать Maven без глобальной установки.

---

## 🎯 Архитектурные слои

```
┌─────────────────────────────────────┐
│         Frontend (HTML)             │  ← Пользовательский интерфейс
└──────────────┬──────────────────────┘
               │ HTTP (REST API)
┌──────────────▼──────────────────────┐
│    Controller (REST Endpoints)      │  ← Обработка HTTP запросов
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Service (Business Logic)       │  ← Бизнес-логика
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│    Repository (Data Access)         │  ← Работа с БД
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│        Database (H2)                │  ← Хранение данных
└─────────────────────────────────────┘
```

---

## 📊 Потоки данных

### Создание купона:
```
1. Пользователь → Форма → JavaScript
2. POST /api/coupons + JSON
3. CouponController.createCoupon()
4. CouponService.createCoupon()
5. CouponService.generateCode()
6. CouponRepository.save()
7. Coupon сохраняется в БД
8. Ответ CouponResponse → JSON
9. JavaScript обновляет UI
```

### Применение купона:
```
1. Пользователь → Форма (код + сумма)
2. POST /api/coupons/{code}/apply + JSON
3. CouponController.applyCoupon()
4. CouponService.validateAndApply()
5. Проверки: active, expiryDate, usageLimit, minOrderAmount
6. CouponService.calculateDiscount()
7. Обновление usageCount
8. CouponRepository.save()
9. Ответ ValidationResponse → JSON
10. JavaScript показывает результат
```

---

## 📝 Размер проекта

| Категория | Количество |
|-----------|------------|
| Java классы | 15 |
| REST endpoints | 5 |
| HTML страниц | 1 |
| Строк Java кода | ~500 |
| Строк HTML/JS | ~400 |
| Зависимостей Maven | 6 |

---

## 🚀 Порядок загрузки при запуске

```
1. mvnw.cmd spring-boot:run
2. Maven Wrapper скачивает Maven (если нужно)
3. Maven скачивает зависимости (Spring Boot, H2, и т.д.)
4. Компиляция Java классов
5. Запуск Spring Boot
6. Инициализация Spring Context
7. Создание Bean'ов (Controller, Service, Repository)
8. Настройка H2 Database (in-memory)
9. JPA создает таблицы (из Entity классов)
10. Запуск Tomcat сервера на порту 8080
11. ✅ Приложение готово к работе
```

---

## 🎓 Для презентации преподавателю

**Покажите эту структуру чтобы продемонстрировать:**

✅ **Архитектура MVC** (Model-View-Controller)
✅ **Layered Architecture** (Controller → Service → Repository)
✅ **RESTful API** дизайн
✅ **Separation of Concerns** (разделение ответственности)
✅ **DTO Pattern** (отделение API от Entity)
✅ **Exception Handling** (централизованная обработка ошибок)
✅ **Dependency Injection** (через Spring)
✅ **ORM** (JPA/Hibernate)

---

Эта структура соответствует лучшим практикам разработки Java/Spring Boot приложений! 🎯
