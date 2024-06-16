package com.colvir.bootcamp.salary.controller;

import com.colvir.bootcamp.salary.dto.ErrorResponse;
import com.colvir.bootcamp.salary.exception.RecordExistsException;
import com.colvir.bootcamp.salary.exception.RecordNotExistsException;
import com.colvir.bootcamp.salary.exception.SpawnedRecordsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.colvir.bootcamp.salary.model.InternalErrorStatus.*;

@RestControllerAdvice
public class ExceptionHandlerController {

    // Ошибка: Запись не найдена (при чтении, изменении)
    @ExceptionHandler(RecordNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotExistsException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(RECORD_DOES_NOT_EXISTS, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Ошибка: Запись уже существует (при добавлении новой)
    @ExceptionHandler(RecordExistsException.class)
    public ResponseEntity<ErrorResponse> handleRecordExistsException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(RECORD_ALREADY_EXISTS, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }

    // Ошибка: Существуют порожденные записи (при удалении)
    @ExceptionHandler(SpawnedRecordsException.class)
    public ResponseEntity<ErrorResponse> handleSpawnedRecordsException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(THERE_ARE_SPAWNED_RECORDS, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.FAILED_DEPENDENCY);
    }

}
