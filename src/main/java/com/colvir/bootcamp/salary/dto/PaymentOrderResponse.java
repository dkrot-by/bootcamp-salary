/*
Ответ сервиса: Платеж
* */
package com.colvir.bootcamp.salary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PaymentOrderResponse {

    private Integer id;
    private Integer workerId;
    private String workerName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private float sum;

}
