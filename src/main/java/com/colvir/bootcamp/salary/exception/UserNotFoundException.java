/*
Ошибка: Пользователь не найден
*/
package com.colvir.bootcamp.salary.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
