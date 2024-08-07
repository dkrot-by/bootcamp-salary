# Bootcamp Java проект "Выплаты"
## Описание
Обучающий проект демонстрирует `Java Spring Boot` приложение, взаимодействовать
с которым можно через `REST API`

В базе данных реализована структура для хранения сущностей:
* Подразделения (некоторой организации)
* Работники: каждый работник относится к одному из подразделению
* Выплаты: каждая выплата относится к одному работнику

### Атрибуты Подразделения:
* Наименование

### Атрибуты Работника:
* Подразделение, к которому он относится
* Имя
* Размер заработной платы

### Атрибуты Выплаты:
* Работник, которму выполняется выплата
* Дата
* Сумма

Можно получить как общий список всех подразделений, работников или выплат,
так и одну конкретную запись по ее идентификатору

Также, записи можно добавлять, изменять и удалять для всех сущностей
(реализован `CRUD`)

Настроены пользователи и разграничены права на операции с помощью `Spring Security`:
* Получение записей (запросы `GET`): регистрация не требуется
* Добавление и изменение записей (запросы `POST` и `PUT`): пользователь с ролью `USER`
* Удаление записей (запросы `DELETE`): пользователь с ролью `ADMIN`

Предусмотрено кеширование запросов чтения для разгрузки базы данных

## Компоненты
* База данных `Postgresql`
* `Liquibase` для создания структуры базы данных
* Для проверки создания структуры базы данных используется база данных `H2`
* Кеширование `Redis`
* Unit-тесты, покрывающие сервисный слой и контроллеры
* `Spring Security`
* Приложение на `Java 17`

## Настройка
База данных (файл `application.properties`):
* `spring.datasource.url`: по умолчанию `jdbc:postgresql://localhost:5432/postgres`
* `spring.datasource.username`: по умолчанию `postgres`
* `spring.datasource.password`: по умолчанию `postgres`

Redis (файл `application.properties`):
* `spring.data.redis.host`: по умолчанию `localhost`
* `spring.data.redis.port`: по умолчанию `6379`

Пользователи
* Таблица в базе данных `users`:
    * `username`: логин пользователя
    * `password`: пароль
    * `roles`: роли пользователя (разделитель запятая)

По умолчанию добавлены пользователи:
1. Администратор:
    * Логин: `admin`
    * Пароль: `admin`
    * Роли: `ADMIN` и `USER`
2. Пользователь:
    * Логин: `user`
    * Пароль: `user`
    * Роль: `USER`

## Установка
### Вручную
Для сборки приложения используется `Maven`

Для запуска приложения требуется `Postgresql`

Для кеширования можно подключить `Redis`

### Docker compose
В корневой папке проекта лежит файл `docker-compose.yml`, который позволяет развернуть
подготовить для работы и запустить:
* Postgresql
* Redis
* Приложение

Команда для первого запуска через командную строку (выполнять в папке проекта):
```shell
docker-compose up --build
```
для последующего запуска:
```shell
docker-compose up
```
для остановки:
```shell
docker-compose down
```

## Точки входа
Актуальное описание `Swagger`:
* [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)

> `localhost` нужно заменить на свой хост

### API управления Подразделениями:
#### [http://localhost:8080/api/salary/dep/](http://localhost:8080/api/salary/dep/) `GET`: список всех подразделений

Пример успешного **HTTP** ответа с кодом **200** в формате `JSON`:
```json
{
  "departments": [
    {
      "id": 1,
      "name": "Department 1"
    },
    {
      "id": 2,
      "name": "Department 2"
    }
  ]
}
```

#### [http://localhost:8080/api/salary/dep/{id}](http://localhost:8080/api/salary/dep/{1}) `GET`: получить подразделение по идентификатору **id**

Пример успешного **HTTP** ответа с кодом **302** в формате `JSON`:
```json
{
  "id": 1,
  "name": "Department 1"
}
```

Если запись отсутствует, будет получен **HTTP** ответ с кодом **404**:
```json
{
  "status": "RECORD_DOES_NOT_EXISTS",
  "message": "Подразделение с id = 5 не найдено"
}
```

#### [http://localhost:8080/api/salary/dep/](http://localhost:8080/api/salary/dep/) `POST`: добавить новую запись
Пример запроса в формате `JSON`:
```json
{
  "name": "Department 1"
}
```

Пример успешного **HTTP** ответа с кодом **201** в формате `JSON`:
```json
{
  "id": 1,
  "name": "Department 1"
}
```

#### [http://localhost:8080/api/salary/dep/](http://localhost:8080/api/salary/dep/) `PUT`: изменить запись
Пример запроса в формате `JSON`:
```json
{
  "id": 1,
  "name": "Department 1"
}
```
Пример успешного **HTTP** ответа с кодом **200** в формате `JSON`:
```json
{
  "id": 1,
  "name": "Department 1"
}
```
Если запись отсутствует, будет получен **HTTP** ответ с кодом **404**:
```json
{
  "status": "RECORD_DOES_NOT_EXISTS",
  "message": "Подразделение с id = 1 не найдено"
}
```

#### [http://localhost:8080/api/salary/dep/{id}](http://localhost:8080/api/salary/dep/{1}) `DELETE`: удалить запись с идентификатором id
Пример успешного **HTTP** ответа с кодом **200** в формате `JSON`:
```json
{
  "id": 1,
  "name": "Department 1"
}
```
Если запись отсутствует, будет получен **HTTP** ответ с кодом **404**:
```json
{
  "status": "RECORD_DOES_NOT_EXISTS",
  "message": "Подразделение с id = 1 не найдено"
}
```

### API управления Работниками:
#### [http://localhost:8080/api/salary/worker/](http://localhost:8080/api/salary/worker/)

Все аналогично, как и для Подразделения
Структура сущности `JSON`:
```json
{
  "id": 0,
  "departmentId": 0,
  "name": "string",
  "salary": 0
}
```

### API управления Выплатами:
#### [http://localhost:8080/api/salary/payment/](http://localhost:8080/api/salary/payment/)

Все аналогично, как и для Подразделения
Структура сущности `JSON`:
```json
{
  "id": 0,
  "workerId": 0,
  "date": "2024-07-31T20:23:55.162Z",
  "sum": 0
}
```
