/*
Ответ сервиса: Платеж
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PaymentOrderResponse {

    private Integer id;
    private Integer workerId;
    private String workerName;
    private Date date;
    private float sum;

}
