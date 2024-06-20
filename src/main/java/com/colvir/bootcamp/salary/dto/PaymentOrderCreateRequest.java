/*
Запрос к сервису: создание Платежа
 */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PaymentOrderCreateRequest {

    private Integer workerId;
    private Date date;
    private float sum;

}
