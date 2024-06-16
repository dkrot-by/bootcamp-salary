/*
Ошибка: Существуют порожденные записи (при удалении)
* */
package com.colvir.bootcamp.salary.exception;

public class SpawnedRecordsException extends RuntimeException {

    public SpawnedRecordsException(String message) {
        super(message);
    }

}
