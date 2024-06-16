/*
Запрос к сервису: Подразделение
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentRequest {

    private Integer id;
    private String name;

}
