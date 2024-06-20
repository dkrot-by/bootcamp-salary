/*
Запрос к сервису: создание Работника
 */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerCreateRequest {

    private Integer departmentId;
    private String name;
    private float salary;

}
