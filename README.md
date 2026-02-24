# OnlineCourseLab

## Возможности
- ✅ Полный CRUD для управления курсами
- ✅ Поиск курсов по автору
- ✅ Создание и обновление курсов
- ✅ REST API для работы с данными курсов
- ✅ Интеграция с H2 Database
- ✅ Проверка стиля кода через Checkstyle (Google Java Style)

## API Endpoints

### Курсы
| Метод | Endpoint | Описание |
|-------|----------|----------|
| `POST` | `/courses` | Создание нового курса |
| `GET` | `/courses` | Получение списка всех курсов |
| `GET` | `/courses/{id}` | Получение курса по ID |
| `GET` | `/courses/search?author=` | Поиск курсов по автору |
| `PUT` | `/courses/{id}` | Полное обновление данных курса |
| `DELETE` | `/courses/{id}` | Удаление курса |

## Архитектура приложения
Проект построен по принципам многослойной архитектуры:
- **Controller** — обработка HTTP-запросов (`CourseController`)
- **Service** — бизнес-логика (`CourseService`, `CourseServiceImpl`)
- **Repository** — взаимодействие с базой данных (`CourseRepository`)
- **Domain** — сущности базы данных (`Course`)
- **DTO** — объекты для передачи данных (`CourseRequestDto`, `CourseResponseDto`)
- **Mapper** — преобразование сущностей в DTO (`CourseMapper`)

## Технологический стек
- **Java 25**
- **Spring Boot 4.0.3**
- **Spring Data JPA**
- **Spring Web MVC**
- **H2 Database**
- **Lombok**
- **Maven**
- **Checkstyle** (Google Java Style)
