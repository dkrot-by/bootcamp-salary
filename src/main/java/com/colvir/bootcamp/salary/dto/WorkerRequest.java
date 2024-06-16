/*
Запрос к сервису: Работник
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerRequest {

    private Integer id;
    private Integer departmentId;
    private String name;
    private float salary;

}
