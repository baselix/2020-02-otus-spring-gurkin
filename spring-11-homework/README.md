# spring-12-homework - домашнее задание №12

В CRUD Web-приложение добавить механизм аутентификации

Цель: защитить Web-приложение аутентифкацией и простой авторизацией

Результат: приложение с использованием Spring Security

Описание задания: Задание выполняется на основе нереактивного приложения Sping MVC!

Требования:
1. Добавить в приложение новую сущность - пользователь. Не обязательно реализовывать методы по созданию пользователей - допустимо добавить пользователей только через БД-скрипты.
2. В существующее CRUD-приложение добавить механизм Form-based аутентификации.
3. UsersServices реализовать самостоятельно.
4. Авторизация на всех страницах - для всех аутентифицированных. Форма логина - доступна для всех.

Описание решения:

1. Для выполнения задания создано новое приложение Journal, позволяющее пользователю создавать заметки.
   Пользователь может видеть только свои заметки, заметки других пользователей ему не видны.
2. Аутентификация выполнена на основе Form-based
3. Пользователи хранятся в бд
4. Корневая страница приложения доступна как для аутентифицированных пользователей, так и для анонимных, остальные страницы - только для аутентифицированных.