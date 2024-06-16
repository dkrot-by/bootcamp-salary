/*
Ответ сервиса: Подразделение
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentResponse {

    private Integer id;
    private String name;

}
