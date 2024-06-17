/*
Репозиторий для работы с Платежными поручениями:
  - Хранение реализовано в списке
* */
package com.colvir.bootcamp.salary.repository;

import com.colvir.bootcamp.salary.model.PaymentOrder;
import com.colvir.bootcamp.salary.model.Worker;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PaymentOrderRepository {

    private final Set<PaymentOrder> paymentOrders = new HashSet<>();

    // Создание записи
    public void save(PaymentOrder paymentOrder) {
        paymentOrders.add(paymentOrder);
    }

    // Получение всех записей
    public List<PaymentOrder> getAll() {
        return new ArrayList<>(paymentOrders);
    }

    // Получение записи
    public PaymentOrder getById(Integer id) {
        return paymentOrders.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Получение списка записей по Работнику
    public List<PaymentOrder> getByWorker(Worker worker) {
        return paymentOrders.stream()
                .filter(p -> p.getWorker().getId().equals(worker.getId()))
                .collect(Collectors.toList());
    }

    // Обновление записи
    public void update(PaymentOrder paymentOrder) {
        PaymentOrder paymentOrderInStorage = getById(paymentOrder.getId());
        paymentOrderInStorage.setDate(paymentOrder.getDate());
        paymentOrderInStorage.setSum(paymentOrder.getSum());
        paymentOrderInStorage.setWorker(paymentOrder.getWorker());
    }

    // Удаление записи
    public void delete(Integer id) {
        paymentOrders.remove(getById(id));
    }

}
