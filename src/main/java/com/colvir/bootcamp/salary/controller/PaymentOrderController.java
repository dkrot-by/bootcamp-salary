/*
API для управления списком Платежных поручений
В контроллере настроены вызовы по http и реализованы только вызовы сервисной части
*/
package com.colvir.bootcamp.salary.controller;

import com.colvir.bootcamp.salary.dto.PaymentOrderCreateRequest;
import com.colvir.bootcamp.salary.dto.PaymentOrderListResponse;
import com.colvir.bootcamp.salary.dto.PaymentOrderResponse;
import com.colvir.bootcamp.salary.dto.PaymentOrderUpdateRequest;
import com.colvir.bootcamp.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("salary/payment")
@RequiredArgsConstructor
public class PaymentOrderController {

    private final SalaryService salaryService;

    // Создание (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentOrderResponse paymentOrderCreate(@RequestBody PaymentOrderCreateRequest request) {
        return salaryService.paymentOrderCreate(request);
    }

    // Получение одной записи (GET)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public PaymentOrderResponse paymentOrderGetById(@PathVariable("id") Integer id) {
        return salaryService.paymentOrderGetById(id);
    }

    // Получение всех записей (GET)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaymentOrderListResponse paymentOrderGetAll() {
        return salaryService.paymentOrderGetAll();
    }

    // Изменение записи (PUT)
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PaymentOrderResponse paymentOrderUpdate(@RequestBody PaymentOrderUpdateRequest request) {
        return salaryService.paymentOrderUpdate(request);
    }

    // Удаление записи (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentOrderResponse paymentOrderDelete(@PathVariable("id") Integer id) {
        return salaryService.paymentOrderDelete(id);
    }

}
