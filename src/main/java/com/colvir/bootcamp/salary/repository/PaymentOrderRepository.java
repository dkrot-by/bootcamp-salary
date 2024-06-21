/*
Репозиторий для работы с Платежными поручениями:
  - Хранение реализовано в БД
* */
package com.colvir.bootcamp.salary.repository;

import com.colvir.bootcamp.salary.model.PaymentOrder;
import com.colvir.bootcamp.salary.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Integer> {

    List<PaymentOrder> findAllByWorker(Worker worker);

}
