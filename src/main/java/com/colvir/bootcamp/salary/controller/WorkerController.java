/*
API для управления списком Работников
В контроллере настроены вызовы по http и реализованы только вызовы сервисной части
*/
package com.colvir.bootcamp.salary.controller;

import com.colvir.bootcamp.salary.dto.WorkerCreateRequest;
import com.colvir.bootcamp.salary.dto.WorkerListResponse;
import com.colvir.bootcamp.salary.dto.WorkerResponse;
import com.colvir.bootcamp.salary.dto.WorkerUpdateRequest;
import com.colvir.bootcamp.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("salary/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final SalaryService salaryService;

    // Создание (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkerResponse workerCreate(@RequestBody WorkerCreateRequest request) {
        return salaryService.workerCreate(request);
    }

    // Получение одной записи (GET)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public WorkerResponse workerGetById(@PathVariable("id") Integer id) {
        return salaryService.workerGetById(id);
    }

    // Получение всех записей (GET)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public WorkerListResponse workerGetAll() {
        return salaryService.workerGetAll();
    }

    // Изменение записи (PUT)
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public WorkerResponse workerUpdate(@RequestBody WorkerUpdateRequest request) {
        return salaryService.workerUpdate(request);
    }

    // Удаление записи (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkerResponse workerDelete(@PathVariable("id") Integer id) {
        return salaryService.workerDelete(id);
    }

}
