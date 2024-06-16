/*
В контроллере настроены вызовы по http и реализованы только вызовы сервисной части
* */
package com.colvir.bootcamp.salary.controller;

import com.colvir.bootcamp.salary.dto.*;
import com.colvir.bootcamp.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Подразделение

    // Создание (POST)
    @PostMapping("/dep/")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse departmentCreate(@RequestBody DepartmentRequest request) {
        return salaryService.departmentCreate(request);
    }

    // Получение одной записи (GET)
    @GetMapping("/dep/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public DepartmentResponse departmentRead(@PathVariable("id") Integer id) {
        return salaryService.departmentRead(id);
    }

    // Получение всех записей (GET)
    @GetMapping("/dep/")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentListResponse departmentRead() {
        return salaryService.departmentRead();
    }

    // Изменение записи (PUT)
    @PutMapping("/dep/")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse departmentUpdate(@RequestBody DepartmentRequest request) {
        return salaryService.departmentUpdate(request);
    }

    // Удаление записи (DELETE)
    @DeleteMapping("/dep/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse departmentDelete(@PathVariable("id") Integer id) {
        return salaryService.departmentDelete(id);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Работник

    // Создание (POST)
    @PostMapping("/worker/")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkerResponse workerCreate(@RequestBody WorkerRequest request) {
        return salaryService.workerCreate(request);
    }

    // Получение одной записи (GET)
    @GetMapping("/worker/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public WorkerResponse workerRead(@PathVariable("id") Integer id) {
        return salaryService.workerRead(id);
    }

    // Получение всех записей (GET)
    @GetMapping("/worker/")
    @ResponseStatus(HttpStatus.OK)
    public WorkerListResponse workerRead() {
        return salaryService.workerRead();
    }

    // Изменение записи (PUT)
    @PutMapping("/worker/")
    @ResponseStatus(HttpStatus.OK)
    public WorkerResponse workerUpdate(@RequestBody WorkerRequest request) {
        return salaryService.workerUpdate(request);
    }

    // Удаление записи (DELETE)
    @DeleteMapping("/worker/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkerResponse workerDelete(@PathVariable("id") Integer id) {
        return salaryService.workerDelete(id);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Платежное поручение

    // Создание (POST)
    @PostMapping("/payment/")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentOrderResponse paymentOrderCreate(@RequestBody PaymentOrderRequest request) {
        return salaryService.paymentOrderCreate(request);
    }

    // Получение одной записи (GET)
    @GetMapping("/payment/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public PaymentOrderResponse paymentOrderRead(@PathVariable("id") Integer id) {
        return salaryService.paymentOrderRead(id);
    }

    // Получение всех записей (GET)
    @GetMapping("/payment/")
    @ResponseStatus(HttpStatus.OK)
    public PaymentOrderListResponse paymentOrderRead() {
        return salaryService.paymentOrderRead();
    }

    // Изменение записи (PUT)
    @PutMapping("/payment/")
    @ResponseStatus(HttpStatus.OK)
    public PaymentOrderResponse paymentOrderUpdate(@RequestBody PaymentOrderRequest request) {
        return salaryService.paymentOrderUpdate(request);
    }

    // Удаление записи (DELETE)
    @DeleteMapping("/payment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentOrderResponse paymentOrderDelete(@PathVariable("id") Integer id) {
        return salaryService.paymentOrderDelete(id);
    }

}