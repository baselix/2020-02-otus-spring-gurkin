# spring-16-homework - домашнее задание №16

Использовать метрики, healthchecks и logfile

Цель: реализовать production-grade мониторинг и прозрачность в приложении

Результат: приложение с применением Spring Boot Actuator

Описание задания: Задание выполняется на основе нереактивного приложения Sping MVC!

Требования:
1. Подключить Spring Boot Actuator в приложение.
2. Включить метрики, healthchecks и logfile.
3. Реализовать свой собственный HealthCheck индикатор
4. UI для данных от Spring Boot Actuator реализовывать не нужно.
5. Опционально: переписать приложение на HATEOAS принципах с помощью Spring Data REST Repository

Описание решения:

1. Подключен Spring Boot Actuator
2. Метрики и healthchecks включены, добавлен logback.xml
3. Создан собственный DayTimeHealthIndicator, который с 9 до 21 часа возвращает "status": "UP"
    а в остальное время суток "status": "DOWN"