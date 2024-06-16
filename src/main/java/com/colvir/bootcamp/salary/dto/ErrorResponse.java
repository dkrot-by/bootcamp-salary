package com.colvir.bootcamp.salary.dto;

import com.colvir.bootcamp.salary.model.InternalErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private InternalErrorStatus status;
    private String message;

}
