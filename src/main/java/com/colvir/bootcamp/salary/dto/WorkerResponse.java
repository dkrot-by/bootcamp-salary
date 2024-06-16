/*
Ответ сервиса: Работник
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerResponse {

    private Integer id;
    private Integer departmentId;
    private String departmentName;
    private String name;
    private float salary;

}
