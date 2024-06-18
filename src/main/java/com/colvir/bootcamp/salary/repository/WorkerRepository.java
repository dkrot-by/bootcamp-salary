/*
Репозиторий для работы с Работниками:
  - Хранение реализовано в БД
* */
package com.colvir.bootcamp.salary.repository;

import com.colvir.bootcamp.salary.model.Department;
import com.colvir.bootcamp.salary.model.Worker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class WorkerRepository {

    private final SessionFactory sessionFactory;

    // Создание записи
    public Worker save(Worker worker) {
        sessionFactory.getCurrentSession().persist(worker);
        return worker;
    }

    // Получение всех записей
    public List<Worker> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select d from Worker d", Worker.class).getResultList();
    }

    // Получение записи
    public Worker getById(Integer id) {
        if (id == null) {
            return null;
        }
        Session session = sessionFactory.getCurrentSession();
        return session.get(Worker.class, id);
    }

    // Получение списка записей по Подразделению
    public List<Worker> getByDepartment(Department department) {
        Session session = sessionFactory.getCurrentSession();
        return new ArrayList<>(session.createQuery("select w from Worker w where w.department.id = :id", Worker.class)
                .setParameter("id", department.getId())
                .getResultList());
    }

    // Обновление записи
    public Worker update(Worker worker) {
        Session session = sessionFactory.getCurrentSession();
        Worker workerResult = session.get(Worker.class, worker.getId());
        workerResult.setName(worker.getName());
        workerResult.setSalary(worker.getSalary());
        return workerResult;
    }

    // Удаление записи
    public Worker delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Worker workerResult = session.get(Worker.class, id);
        session.remove(workerResult);
        return workerResult;
    }

}
