/*
Ошибка: Запись уже существует (при добавлении новой)
* */
package com.colvir.bootcamp.salary.exception;

public class RecordExistsException extends RuntimeException {

    public RecordExistsException(String message) {
        super(message);
    }

}
