/*
Ответ сервиса: Список платежей
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentOrderListResponse {

    private List<PaymentOrderResponse> paymentOrders;

}
