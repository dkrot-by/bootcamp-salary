/*
Сущность "Работник":
  1) Работник привязан к одному Подразделению (Department)
  2) У работника есть множество Платежных поручений (PaymentOrder)
* */
package com.colvir.bootcamp.salary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worker {

    private Integer id;
    private String name;
    private float salary;
    private Department department;
    private List<PaymentOrder> paymentOrders;

}
