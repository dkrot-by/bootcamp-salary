/*
API для управления списком Подразделений
В контроллере настроены вызовы по http и реализованы только вызовы сервисной части
*/
package com.colvir.bootcamp.salary.controller;

import com.colvir.bootcamp.salary.dto.DepartmentCreateRequest;
import com.colvir.bootcamp.salary.dto.DepartmentListResponse;
import com.colvir.bootcamp.salary.dto.DepartmentResponse;
import com.colvir.bootcamp.salary.dto.DepartmentUpdateRequest;
import com.colvir.bootcamp.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("salary/dep")
@RequiredArgsConstructor
public class DepartmentController {

    private final SalaryService salaryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse departmentCreate(@RequestBody DepartmentCreateRequest request) {
        return salaryService.departmentCreate(request);
    }

    // Получение одной записи (GET)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public DepartmentResponse departmentGetById(@PathVariable("id") Integer id) {
        return salaryService.departmentGetById(id);
    }

    // Получение всех записей (GET)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public DepartmentListResponse departmentGetAll() {
        return salaryService.departmentGetAll();
    }

    // Изменение записи (PUT)
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse departmentUpdate(@RequestBody DepartmentUpdateRequest request) {
        return salaryService.departmentUpdate(request);
    }

    // Удаление записи (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse departmentDelete(@PathVariable("id") Integer id) {
        return salaryService.departmentDelete(id);
    }

}
