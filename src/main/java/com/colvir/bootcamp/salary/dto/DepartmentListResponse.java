/*
Ответ сервиса: Список подразделений
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DepartmentListResponse {

    private List<DepartmentResponse> departments;

}
