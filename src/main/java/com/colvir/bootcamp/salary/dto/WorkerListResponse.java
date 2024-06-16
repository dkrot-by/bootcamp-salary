/*
Ответ сервиса: Список работников
* */
package com.colvir.bootcamp.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkerListResponse {

    private List<WorkerResponse> workers;

}
