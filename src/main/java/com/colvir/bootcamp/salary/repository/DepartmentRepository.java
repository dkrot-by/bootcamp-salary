/*
Репозиторий для работы с Подразделениями:
  - Хранение реализовано в БД
* */
package com.colvir.bootcamp.salary.repository;

import com.colvir.bootcamp.salary.model.Department;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class DepartmentRepository {

    private final SessionFactory sessionFactory;

    // Создание записи
    public Department save(Department department) {
        sessionFactory.getCurrentSession().persist(department);
        return department;
    }

    // Получение всех записей
    public List<Department> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select d from Department d", Department.class).getResultList();
    }

    // Получение записи
    public Department getById(Integer id) {
        if (id == null) {
            return null;
        }
        Session session = sessionFactory.getCurrentSession();
        return session.get(Department.class, id);
    }

    // Обновление записи
    public Department update(Department department) {
        Session session = sessionFactory.getCurrentSession();
        Department departmentForUpdate = session.get(Department.class, department.getId());
        departmentForUpdate.setName(department.getName());
        return departmentForUpdate;
    }

    // Удаление записи
    public Department delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Department departmentResult = session.get(Department.class, id);
        session.remove(departmentResult);
        return departmentResult;
    }

}
