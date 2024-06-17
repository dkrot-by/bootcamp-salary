/*
Репозиторий для работы с Работниками:
  - Хранение реализовано в списке
* */
package com.colvir.bootcamp.salary.repository;

import com.colvir.bootcamp.salary.model.Department;
import com.colvir.bootcamp.salary.model.Worker;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class WorkerRepository {

    private final Set<Worker> workers = new HashSet<>();

    // Создание записи
    public void save(Worker worker) {
        workers.add(worker);
    }

    // Получение всех записей
    public List<Worker> getAll() {
        return new ArrayList<>(workers);
    }

    // Получение записи
    public Worker getById(Integer id) {
        return workers.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Получение списка записей по Подразделению
    public List<Worker> getByDepartment(Department department) {
        return workers.stream()
                .filter(w -> w.getDepartment().getId().equals(department.getId()))
                .collect(Collectors.toList());
    }

    // Обновление записи
    public void update(Worker worker) {
        Worker workerInStorage = getById(worker.getId());
        workerInStorage.setName(worker.getName());
        workerInStorage.setSalary(worker.getSalary());
        workerInStorage.setDepartment(worker.getDepartment());
    }

    // Удаление записи
    public void delete(Integer id) {
        workers.remove(getById(id));
    }

}
