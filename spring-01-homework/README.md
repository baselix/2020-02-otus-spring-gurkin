# spring-01-homework - домашнее задание №1

Программа по проведению тестирования студентов
Цель: создать приложение с помощью Spring IoC, чтобы познакомиться с основной функциональностью IoC, на которой строится весь Spring. Результат: простое приложение, сконфигурированное XML-контекстом.
Описание задание:

В ресурсах хранятся вопросы и различные ответы к ним в виде CSV файла (5 вопрсов).
Программа должна спросить у пользователя фамилию и имя, спросить 5 вопросов из CSV-файла и вывести результат тестирования.
Вопросы могут быть с выбором из нескольких вариантов или со свободным ответом - на Ваше желание и усмотрение.

Требования:
1. Все сервисы в программе должны решать строго определённую задачу.
2. Контекст описывается XML-файлом.
3. Все зависимости должны быть настроены в IoC контейнере.
4. Имя ресурса с вопросами (CSV-файла) необходимо захардкодить в XML-файле с контекстом.
5. CSV с вопросами читается именно как ресурс, а не как файл.
6. Scanner и стандартные типы в контекст класть не нужно!
7. Крайне желательно написать юнит-тест какого-нибудь сервиса (оцениваться будет только попытка написать тест).

Описание решения:

ru.gurkin.spring.usertesting.Main - основной класс приложения, создающий экземпляр ClassPathXmlApplicationContext с помощью которого получается экземпляр сервиса UserTestingService. 
Вызов метода startTesting() этого сервиса запускает процесс тестирования пользователя.

ru.gurkin.spring.usertesting.service - пакет, содержащий инрерфейсы сервисов приложения
	UserTestingService - интерфейс сервиса тестирования пользователей. Описывает единственный метод startTesting() запускающий тестирование.
	UserInterfaceService - интерфейс сервиса взаимодействия с пользователем. Описывает методы, позволяющие получить данные от пользователя или отобразить их ему.

ru.gurkin.spring.usertesting.service.impl - пакет, содержащий имплементации сервисов UserTestingService и UserInterfaceService.
	UserInterfaceServiceImpl - Класс для взаимодействия с пользователем посредством консоли
	UserTestingServiceImpl - Класс реализации сервиса для тестирования пользователей. Инициирует получение данных пользователя, вывод приветствия, вопросов, обработку ответов пользователя и вывод прощания.

ru.gurkin.spring.usertesting.model - пакет, содержащий модели данных
	Question - вопрос теста. Содержит текст вопроса и варианты ответа (опционально)
	Result - модель описывает один из возможных результатов тестирования. Состоит из строки результата и верхней и нижней границ оценки
	ResultValue - модель описывает вариант ответа и соответствующую ему оценку
	TestResults - модель содержит список возможных результатов тестирования с верхней и нижней границами соответствующей им оценки и список значений оценки для вариантов ответов.
	User - Модель пользователя. Содержит данные об имени и фамилии
	UserTest - Модель теста. Содержит вопросы, строки приветствия и прощания, данные для вычисления результатов.

ru.gurkin.spring.usertesting.dao - пакет, содержащий интерфейсы сервиса работы с данными
	TestServiceDao - интерфейс сервиса получения данных теста
	
ru.gurkin.spring.usertesting.dao.impl - пакет, содержащий реализации TestServiceDao:
	CSVTestServiceDaoImpl - имплементация, получающая данные о тесте из .csv файла, лежащего папке ресурсов приложения (имя файла берется из значения параметра resources.test-file-name файла application.properties)
	TurnerTestServiceDaoImpl - имплементация, создающая данные теста внутри себя путем создания и заполнения моделей(тестово-юмористическая реализация, использовалась на ранних этапах создания приложения)