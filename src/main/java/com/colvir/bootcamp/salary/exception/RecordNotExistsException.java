/*
Ошибка: Запись не найдена (при чтении, изменении)
*/
package com.colvir.bootcamp.salary.exception;

public class RecordNotExistsException extends RuntimeException {

    public RecordNotExistsException(String message) {
        super(message);
    }

}
