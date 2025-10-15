# E-Commerce Coupon Service

Веб-приложение для генерации и управления купонами и промоакциями для интернет-магазина.

## 📋 Описание

Это REST API сервис, разработанный на Java Spring Boot, который позволяет:
- Создавать купоны со скидками (процентные, фиксированные, бесплатная доставка)
- Управлять купонами (просмотр, удаление)
- Валидировать и применять купоны к заказам
- Отслеживать использование купонов

Проект включает веб-интерфейс на Tailwind CSS для демонстрации функционала.

## 🛠 Технологический стек

### Backend:
- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Maven**
- **Lombok**

### Frontend:
- **HTML5**
- **Tailwind CSS 3.x** (CDN)
- **Alpine.js 3.x** (CDN)
- **Vanilla JavaScript**

## 📦 Требования

Перед запуском убедитесь, что у вас установлены:

1. **Java 17 или выше**
   - Проверка: `java -version`
   - Скачать: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) или [OpenJDK](https://adoptium.net/)

2. **Maven 3.6 или выше**
   - Проверка: `mvn -version`
   - Скачать: [Apache Maven](https://maven.apache.org/download.cgi)

## 🚀 Установка и запуск

### Шаг 1: Клонирование проекта

Проект уже находится в папке `coupon-service`

### Шаг 2: Установка зависимостей

```bash
cd coupon-service
mvn clean install
```

### Шаг 3: Запуск приложения

```bash
mvn spring-boot:run
```

Приложение запустится на `http://localhost:8080`

### Шаг 4: Открытие веб-интерфейса

Откройте браузер и перейдите по адресу:
- **Frontend:** http://localhost:8080/index.html
- **H2 Console:** http://localhost:8080/h2-console

## 📡 REST API Endpoints

### 1. Создать купон
**POST** `/api/coupons`

**Request Body:**
```json
{
  "discountType": "PERCENTAGE",
  "discountValue": 10.0,
  "minOrderAmount": 1000.0,
  "expiryDate": "2025-12-31T23:59:59",
  "usageLimit": 100
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "code": "SALEXYZ123",
  "discountType": "PERCENTAGE",
  "discountValue": 10.0,
  "minOrderAmount": 1000.0,
  "expiryDate": "2025-12-31T23:59:59",
  "usageLimit": 100,
  "usageCount": 0,
  "active": true,
  "createdAt": "2025-10-15T12:00:00"
}
```

### 2. Получить все купоны
**GET** `/api/coupons`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "code": "SALEXYZ123",
    "discountType": "PERCENTAGE",
    "discountValue": 10.0,
    ...
  }
]
```

### 3. Получить купон по коду
**GET** `/api/coupons/{code}`

**Response (200 OK):**
```json
{
  "id": 1,
  "code": "SALEXYZ123",
  "discountType": "PERCENTAGE",
  ...
}
```

### 4. Применить купон
**POST** `/api/coupons/{code}/apply`

**Request Body:**
```json
{
  "orderAmount": 5000.0
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Купон успешно применен",
  "originalPrice": 5000.0,
  "discount": 500.0,
  "finalPrice": 4500.0
}
```

### 5. Удалить купон
**DELETE** `/api/coupons/{code}`

**Response (204 No Content)**

## 🗂 Структура проекта

```
coupon-service/
├── src/
│   └── main/
│       ├── java/com/ecommerce/coupon/
│       │   ├── CouponServiceApplication.java    # Главный класс
│       │   ├── controller/
│       │   │   └── CouponController.java        # REST API контроллер
│       │   ├── service/
│       │   │   └── CouponService.java           # Бизнес-логика
│       │   ├── repository/
│       │   │   └── CouponRepository.java        # JPA репозиторий
│       │   ├── model/
│       │   │   ├── Coupon.java                  # Entity класс
│       │   │   └── DiscountType.java            # Enum типов скидок
│       │   ├── dto/
│       │   │   ├── CouponCreateRequest.java     # DTO для создания
│       │   │   ├── CouponResponse.java          # DTO для ответа
│       │   │   ├── ValidationRequest.java       # DTO для валидации
│       │   │   └── ValidationResponse.java      # DTO результата
│       │   ├── exception/
│       │   │   ├── CouponNotFoundException.java
│       │   │   ├── CouponExpiredException.java
│       │   │   ├── InvalidCouponException.java
│       │   │   └── GlobalExceptionHandler.java  # Обработчик ошибок
│       │   └── config/
│       │       └── WebConfig.java               # CORS конфигурация
│       └── resources/
│           ├── application.properties           # Конфигурация
│           └── static/
│               └── index.html                   # Веб-интерфейс
├── pom.xml                                      # Maven конфигурация
└── README.md                                    # Документация
```

## 🎯 Функционал

### Типы скидок:
1. **PERCENTAGE** - Процентная скидка (например, 10%)
2. **FIXED_AMOUNT** - Фиксированная сумма (например, 500₽)
3. **FREE_SHIPPING** - Бесплатная доставка

### Бизнес-логика:

#### Создание купона:
- Автоматическая генерация уникального кода (формат: SALE + 6 символов)
- Валидация входных данных
- Установка начальных значений

#### Валидация купона:
- Проверка существования купона
- Проверка активности купона
- Проверка срока действия
- Проверка лимита использований
- Проверка минимальной суммы заказа

#### Применение купона:
- Расчет скидки в зависимости от типа
- Увеличение счетчика использований
- Возврат итоговой цены

## 🎨 Веб-интерфейс

Веб-интерфейс разделен на 3 секции:

1. **Создание купона** - Форма с полями для создания нового купона
2. **Список купонов** - Таблица со всеми купонами и их статусами
3. **Применение купона** - Проверка и применение купона к заказу

### Возможности UI:
- Создание купонов через удобную форму
- Просмотр всех купонов в реальном времени
- Удаление купонов
- Валидация купонов с отображением расчета скидки
- Автообновление списка после операций
- Toast уведомления об успешных/неуспешных операциях
- Адаптивный дизайн (работает на мобильных устройствах)

## 🗄️ База данных

Проект использует **H2 in-memory database**, что означает:
- База данных создается в памяти при запуске приложения
- Не требует установки отдельной СУБД
- Данные сохраняются только во время работы приложения
- При перезапуске все данные удаляются

### Доступ к H2 Console:
1. Откройте http://localhost:8080/h2-console
2. Используйте настройки:
   - **JDBC URL:** `jdbc:h2:mem:coupondb`
   - **User Name:** `sa`
   - **Password:** (оставьте пустым)

## 📝 Примеры использования

### Сценарий 1: Создание процентного купона

1. Откройте http://localhost:8080/index.html
2. В форме "Создать купон":
   - Тип скидки: **Процентная скидка (%)**
   - Процент скидки: **10**
   - Минимальная сумма: **1000**
   - Дата окончания: выберите будущую дату
   - Лимит использований: **100**
3. Нажмите "Создать купон"
4. Купон появится в списке с уникальным кодом (например, SALEABC123)

### Сценарий 2: Применение купона

1. Скопируйте код купона из списка
2. В секции "Применить купон":
   - Код купона: **SALEABC123**
   - Сумма заказа: **5000**
3. Нажмите "Проверить купон"
4. Увидите результат:
   - Исходная цена: 5000₽
   - Скидка: -500₽ (10%)
   - Итоговая цена: 4500₽

### Сценарий 3: Проверка лимитов

1. Примените купон несколько раз
2. Когда лимит использований достигнут, появится ошибка
3. В списке купонов увидите обновленный счетчик использований

## ⚠️ Известные ограничения

1. База данных in-memory (данные не сохраняются после перезапуска)
2. Нет аутентификации/авторизации
3. Нет пагинации для списка купонов
4. FREE_SHIPPING не рассчитывает стоимость доставки (возвращает 0)

## 🔧 Настройка

### Изменение порта сервера

Отредактируйте `src/main/resources/application.properties`:
```properties
server.port=9090
```

### Изменение настроек БД

Для использования постоянной базы данных замените H2 на PostgreSQL/MySQL в `pom.xml` и `application.properties`.

## 🎓 Для демонстрации преподавателю

### Подготовка:
1. Запустите приложение: `mvn spring-boot:run`
2. Откройте браузер: http://localhost:8080/index.html
3. Создайте 2-3 тестовых купона

### Что показать:

1. **Веб-интерфейс:**
   - Создание купона
   - Список купонов
   - Применение купона с расчетом

2. **REST API:**
   - Откройте DevTools (F12) → Network
   - Создайте купон и покажите POST запрос
   - Покажите JSON структуру данных

3. **Код проекта:**
   - Структура проекта (MVC архитектура)
   - Controller → Service → Repository
   - Entity и DTO классы

4. **Функционал:**
   - Валидация (минимальная сумма заказа)
   - Обработка ошибок (истекший купон)
   - Лимит использований

5. **База данных:**
   - Откройте H2 Console
   - Покажите таблицу COUPONS
   - Выполните SQL: `SELECT * FROM COUPONS`

## 📚 Дополнительные материалы

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Tailwind CSS](https://tailwindcss.com/)
- [Alpine.js](https://alpinejs.dev/)

## 👨‍💻 Автор

**Курсовая работа**
Студент: Mark
Тема: E-Commerce Coupon Service

## 📄 Лицензия

Этот проект создан в учебных целях для курсовой работы.
