/*
Репозиторий для работы с Платежными поручениями:
  - Хранение реализовано в БД
* */
package com.colvir.bootcamp.salary.repository;

import com.colvir.bootcamp.salary.model.PaymentOrder;
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
public class PaymentOrderRepository {

    private final SessionFactory sessionFactory;

    // Создание записи
    public PaymentOrder save(PaymentOrder paymentOrder) {
        sessionFactory.getCurrentSession().persist(paymentOrder);
        return paymentOrder;
    }

    // Получение всех записей
    public List<PaymentOrder> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select d from PaymentOrder d", PaymentOrder.class).getResultList();
    }

    // Получение записи
    public PaymentOrder getById(Integer id) {
        if (id == null) {
            return null;
        }
        Session session = sessionFactory.getCurrentSession();
        return session.get(PaymentOrder.class, id);
    }

    // Получение списка записей по Работнику
    public List<PaymentOrder> getByWorker(Worker worker) {
        Session session = sessionFactory.getCurrentSession();
        return new ArrayList<>(session.createQuery("select p from PaymentOrder p where p.worker.id = :id", PaymentOrder.class)
                .setParameter("id", worker.getId())
                .getResultList());
    }

    // Обновление записи
    public PaymentOrder update(PaymentOrder paymentOrder) {
        Session session = sessionFactory.getCurrentSession();
        PaymentOrder paymentResult = session.get(PaymentOrder.class, paymentOrder.getId());
        paymentResult.setDate(paymentOrder.getDate());
        paymentResult.setSum(paymentOrder.getSum());
        return paymentResult;
    }

    // Удаление записи
    public PaymentOrder delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        PaymentOrder paymentResult = session.get(PaymentOrder.class, id);
        session.remove(paymentResult);
        return paymentResult;
    }

}
