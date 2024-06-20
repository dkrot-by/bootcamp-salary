/*
Сущность "Работник":
  1) Работник привязан к одному Подразделению (Department)
  2) У работника есть множество Платежных поручений (PaymentOrder)
* */
package com.colvir.bootcamp.salary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @SequenceGenerator(name = "workers_seq", sequenceName = "workers_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workers_seq")
    private Integer id;
    private String name;
    private float salary;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
    @OneToMany(mappedBy = "worker")
    private List<PaymentOrder> paymentOrders;

}
