/*
Запрос к сервису: изменение Подразделения
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentUpdateRequest {

    private Integer id;
    private String name;

}
