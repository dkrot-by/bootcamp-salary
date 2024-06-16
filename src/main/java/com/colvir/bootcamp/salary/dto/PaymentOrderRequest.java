/*
Запрос к сервису: Платеж
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PaymentOrderRequest {

    private Integer id;
    private Integer workerId;
    private Date date;
    private float sum;

}
