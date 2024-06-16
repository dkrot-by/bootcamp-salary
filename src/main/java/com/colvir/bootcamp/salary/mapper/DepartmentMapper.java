package com.colvir.bootcamp.salary.mapper;

import com.colvir.bootcamp.salary.dto.*;
import com.colvir.bootcamp.salary.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {

    // Запрос -> сущность
    Department requestToDepartment(DepartmentRequest request);

    // Сущность -> ответ
    DepartmentResponse departmentToResponse(Department department);

    // Список из сущностей -> список для ответа
    List<DepartmentResponse> departmentListToResponseList(List<Department> departments);

    default DepartmentListResponse departmentListToResponse(List<Department> departments) {
        return new DepartmentListResponse(departmentListToResponseList(departments));
    }

}
