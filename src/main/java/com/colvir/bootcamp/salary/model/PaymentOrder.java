/*
Сущность "Платежное поручение": Платежное поручение привязано к одному Работнику (Worker)
* */
package com.colvir.bootcamp.salary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrder {

    private Integer id;
    private Date date;
    private float sum;
    private Worker worker;

}
