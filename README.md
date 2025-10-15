# E-Commerce Coupon Service

**Статус:** ✅ **Проект завершен и готов к демонстрации**

Веб-приложение на Java Spring Boot с REST API для генерации и управления купонами и промоакциями для e-commerce магазина.

---

## 🚀 Быстрый старт

### Для Windows (рекомендуется для демонстрации):

```cmd
cd coupon-service
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

Открыть: http://localhost:8080/index.html

### Для Mac/Linux:

```bash
cd coupon-service
./mvnw clean install
./mvnw spring-boot:run
```

Открыть: http://localhost:8080/index.html

---

## 📋 Требования

- **Java 17+** (обязательно!)
- Maven не требуется (используется Maven Wrapper)

**Инструкция по установке Java 17:** См. [WINDOWS_SETUP.md](Documentation/WINDOWS_SETUP.md)

---

## 📊 Статистика проекта

| Метрика | Значение |
|---------|----------|
| Java файлов | 15 |
| Строк кода (Java) | 819 |
| Строк кода (HTML/JS) | 430 |
| REST API endpoints | 5 |
| Время разработки | ~2 часа |
| Статус | ✅ Готов к защите |

---

## 🛠 Технологии

**Backend:**
- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- H2 Database (in-memory)
- Maven Wrapper

**Frontend:**
- HTML5 + Tailwind CSS + Alpine.js
- Single Page Application

---

## 📚 Документация

Вся подробная документация находится в папке [`Documentation/`](Documentation/):

- **[QUICKSTART.md](Documentation/QUICKSTART.md)** - Быстрый старт (3 команды)
- **[WINDOWS_SETUP.md](Documentation/WINDOWS_SETUP.md)** - Установка Java 17 и запуск на Windows
- **[PLAN.md](Documentation/PLAN.md)** - Полный план реализации с итогами
- **[PROJECT_STRUCTURE.md](Documentation/PROJECT_STRUCTURE.md)** - Архитектура проекта
- **[SUMMARY.md](Documentation/SUMMARY.md)** - Итоговый отчет
- **[CHECKLIST.md](Documentation/CHECKLIST.md)** - Чеклист для демонстрации преподавателю

---

## 🎯 Функционал

### REST API (5 endpoints):
1. `POST /api/coupons` - Создать купон
2. `GET /api/coupons` - Список купонов
3. `GET /api/coupons/{code}` - Получить купон
4. `POST /api/coupons/{code}/apply` - Применить купон
5. `DELETE /api/coupons/{code}` - Удалить купон

### Веб-интерфейс:
- ✅ Создание купонов через форму
- ✅ Список всех купонов в таблице
- ✅ Применение купона с расчетом скидки
- ✅ Удаление купонов
- ✅ Валидация и обработка ошибок

### Типы скидок:
- **PERCENTAGE** - Процентная скидка (10%)
- **FIXED_AMOUNT** - Фиксированная сумма (500₽)
- **FREE_SHIPPING** - Бесплатная доставка

---

## 📁 Структура проекта

```
coupon-service/
├── Documentation/        # Вся документация проекта
├── src/
│   └── main/
│       ├── java/        # 15 Java классов
│       └── resources/   # Конфигурация + Frontend
├── mvnw / mvnw.cmd     # Maven Wrapper
└── pom.xml             # Maven конфигурация
```

---

## ✅ Готовность к защите

- ✅ Все функции реализованы
- ✅ Протестировано на Mac
- ✅ Готово к запуску на Windows
- ✅ Полная документация
- ✅ Красивый веб-интерфейс
- ✅ Качественный код с комментариями

---

## 🎓 Для демонстрации преподавателю

**Что показать:**
1. Запуск приложения (3 команды)
2. Веб-интерфейс (создание, список, применение купонов)
3. REST API в DevTools (Network tab)
4. Код проекта (структура, классы)
5. База данных H2 Console (опционально)

**Подробная инструкция:** [CHECKLIST.md](Documentation/CHECKLIST.md)

---

## 📞 Поддержка

Если возникли проблемы при запуске:

1. Проверьте версию Java: `java -version` (должна быть 17+)
2. Прочитайте [QUICKSTART.md](Documentation/QUICKSTART.md)
3. Для Windows: [WINDOWS_SETUP.md](Documentation/WINDOWS_SETUP.md)

---

## 👨‍💻 Автор

**Курсовая работа**
Студент: Mark
Тема: E-Commerce Coupon Service
Дата: 15 октября 2025

---

**🚀 Удачи на защите!**
