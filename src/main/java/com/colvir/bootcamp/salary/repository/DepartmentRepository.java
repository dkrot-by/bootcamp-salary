/*
Репозиторий для работы с Подразделениями:
  - Хранение реализовано в списке
* */
package com.colvir.bootcamp.salary.repository;

import com.colvir.bootcamp.salary.model.Department;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DepartmentRepository {

    private final Set<Department> departments = new HashSet<>();

    // Создание записи
    public void create(Department department) {
        departments.add(department);
    }

    // Получение всех записей
    public List<Department> read() {
        return new ArrayList<>(departments);
    }

    // Получение записи
    public Department read(Integer id) {
        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Обновление записи
    public void update(Department department) {
        Department departmentInStorage = read(department.getId());
        departmentInStorage.setName(department.getName());
        departmentInStorage.setWorkers(department.getWorkers());
    }

    // Удаление записи
    public void delete(Integer id) {
        departments.remove(read(id));
    }

}
