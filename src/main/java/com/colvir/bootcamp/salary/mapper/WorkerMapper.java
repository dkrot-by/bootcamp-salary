package com.colvir.bootcamp.salary.mapper;

import com.colvir.bootcamp.salary.dto.*;
import com.colvir.bootcamp.salary.model.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkerMapper {

    // Запрос на создание -> сущность
    Worker createRequestToWorker(WorkerCreateRequest request);

    // Запрос на изменение -> сущность
    Worker updateRequestToWorker(WorkerUpdateRequest request);

    // Сущность -> ответ, в сущности есть ссылка на родительский объект Подразделение,
    // поэтому вытягиваем из него в ответ дополнительные поля: id и наименование
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    WorkerResponse workerToResponse(Worker worker);

    // Список из сущностей -> список для ответа
    List<WorkerResponse> workerListToResponseList(List<Worker> workers);

    default WorkerListResponse workerListToResponse(List<Worker> workers) {
        return new WorkerListResponse(workerListToResponseList(workers));
    }

}
