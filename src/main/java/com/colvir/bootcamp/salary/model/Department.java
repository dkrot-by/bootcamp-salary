/*
Сущность "Подразделение": в подразделении есть множество работников (Worker)
* */
package com.colvir.bootcamp.salary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private Integer id;
    private String name;
    private List<Worker> workers;

}
