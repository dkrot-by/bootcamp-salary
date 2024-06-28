/*
Сущность "Подразделение": в подразделении есть множество работников (Worker)
* */
package com.colvir.bootcamp.salary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @SequenceGenerator(name = "dep_seq", sequenceName = "departments_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dep_seq")
    private Integer id;
    private String name;
//TODO: Изучить вопрос: в двух связанных сущностях есть ссылки друг на друга, из-за чего при некоторых авто-выгрузках
//      получается рекурсия(?) (пример, objectMapper.writeValueAsString(worker))
//      пока эта ссылка не нужна нигде
//    @OneToMany(mappedBy = "department")
//    private List<Worker> workers;

}
