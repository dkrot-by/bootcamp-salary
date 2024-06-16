package com.colvir.bootcamp.salary.service;

import com.colvir.bootcamp.salary.dto.*;
import com.colvir.bootcamp.salary.exception.RecordExistsException;
import com.colvir.bootcamp.salary.exception.RecordNotExistsException;
import com.colvir.bootcamp.salary.exception.SpawnedRecordsException;
import com.colvir.bootcamp.salary.mapper.DepartmentMapper;
import com.colvir.bootcamp.salary.mapper.PaymentOrderMapper;
import com.colvir.bootcamp.salary.mapper.WorkerMapper;
import com.colvir.bootcamp.salary.model.Department;
import com.colvir.bootcamp.salary.model.PaymentOrder;
import com.colvir.bootcamp.salary.model.Worker;
import com.colvir.bootcamp.salary.repository.DepartmentRepository;
import com.colvir.bootcamp.salary.repository.PaymentOrderRepository;
import com.colvir.bootcamp.salary.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final DepartmentMapper departmentMapper;
    private final WorkerMapper workerMapper;
    private final PaymentOrderMapper paymentOrderMapper;
    private final DepartmentRepository departmentRepository;
    private final WorkerRepository workerRepository;
    private final PaymentOrderRepository paymentOrderRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Подразделение

    // Создание записи по запросу от API
    public DepartmentResponse departmentCreate(DepartmentRequest request) {
        // Проверка на то, что подразделение уже есть
        if (departmentRepository.read(request.getId()) != null) {
            throw new RecordExistsException(String.format("Подразделение с id = %s уже существует", request.getId()));
        }
        Department department = departmentMapper.requestToDepartment(request);
        departmentRepository.create(department);
        return departmentMapper.departmentToResponse(department);
    }

    // Передача списка записей по запросу от API
    public DepartmentListResponse departmentRead() {
        return departmentMapper.departmentListToResponse(departmentRepository.read());
    }

    // Передача записи по запросу от API
    public DepartmentResponse departmentRead(Integer id) {
        Department department = departmentRepository.read(id);
        if (department == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", id));
        }
        return departmentMapper.departmentToResponse(department);
    }

    // Обновление записи по запросу от API
    public DepartmentResponse departmentUpdate(DepartmentRequest request) {
        if (departmentRepository.read(request.getId()) == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", request.getId()));
        }
        Department department = departmentMapper.requestToDepartment(request);
        departmentRepository.update(department);
        return departmentMapper.departmentToResponse(department);
    }

    // Удаление записи по запросу от API
    public DepartmentResponse departmentDelete(Integer id) {
        Department department = departmentRepository.read(id);
        if (department == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", id));
        }
        if (!workerRepository.read(department).isEmpty()) {
            throw new SpawnedRecordsException(String.format("Удаление не возможно: в подразделении с id = %s есть работники", id));
        }
        DepartmentResponse response = departmentMapper.departmentToResponse(department);
        departmentRepository.delete(id);
        return response;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Работник

    // Создание записи по запросу от API
    public WorkerResponse workerCreate(WorkerRequest request) {
        if (workerRepository.read(request.getId()) != null) {
            throw new RecordExistsException(String.format("Работник с id = %s уже существует", request.getId()));
        }
        Department department = departmentRepository.read(request.getDepartmentId());
        if (department == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", request.getDepartmentId()));
        }
        Worker worker = workerMapper.requestToWorker(request);
        worker.setDepartment(department);
        workerRepository.create(worker);
        return workerMapper.workerToResponse(worker);
    }

    // Передача списка записей по запросу от API
    public WorkerListResponse workerRead() {
        return workerMapper.workerListToResponse(workerRepository.read());
    }

    // Передача записи по запросу от API
    public WorkerResponse workerRead(Integer id) {
        Worker worker = workerRepository.read(id);
        if (worker == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", id));
        }
        return workerMapper.workerToResponse(worker);
    }

    // Обновление записи по запросу от API
    public WorkerResponse workerUpdate(WorkerRequest request) {
        if (workerRepository.read(request.getId()) == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", request.getId()));
        }
        Department department = departmentRepository.read(request.getDepartmentId());
        if (department == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", request.getDepartmentId()));
        }
        Worker worker = workerMapper.requestToWorker(request);
        worker.setDepartment(department);
        workerRepository.update(worker);
        return workerMapper.workerToResponse(worker);
    }

    // Удаление записи по запросу от API
    public WorkerResponse workerDelete(Integer id) {
        Worker worker = workerRepository.read(id);
        if (worker == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", id));
        }
        if (!paymentOrderRepository.read(worker).isEmpty()) {
            throw new SpawnedRecordsException(String.format("Удаление не возможно: у работника с id = %s есть платежные поручения", id));
        }
        WorkerResponse response = workerMapper.workerToResponse(worker);
        workerRepository.delete(id);
        return response;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Платежное поручение

    // Создание записи по запросу от API
    public PaymentOrderResponse paymentOrderCreate(PaymentOrderRequest request) {
        // Проверка на то, что платеж уже есть
        if (paymentOrderRepository.read(request.getId()) != null) {
            throw new RecordExistsException(String.format("Платежное поручение с id = %s уже существует", request.getId()));
        }
        Worker worker = workerRepository.read(request.getWorkerId());
        if (worker == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", request.getWorkerId()));
        }
        PaymentOrder paymentOrder = paymentOrderMapper.RequestToPaymentOrder(request);
        paymentOrder.setWorker(worker);
        paymentOrderRepository.create(paymentOrder);
        return paymentOrderMapper.paymentOrderToResponse(paymentOrder);
    }

    // Передача списка записей по запросу от API
    public PaymentOrderListResponse paymentOrderRead() {
        return paymentOrderMapper.paymentOrderListToResponse(paymentOrderRepository.read());
    }

    // Передача записи по запросу от API
    public PaymentOrderResponse paymentOrderRead(Integer id) {
        PaymentOrder payment = paymentOrderRepository.read(id);
        if (payment == null) {
            throw new RecordNotExistsException(String.format("Платежное поручение с id = %s не найдено", id));
        }
        return paymentOrderMapper.paymentOrderToResponse(payment);
    }

    // Обновление записи по запросу от API
    public PaymentOrderResponse paymentOrderUpdate(PaymentOrderRequest request) {
        if (paymentOrderRepository.read(request.getId()) == null) {
            throw new RecordNotExistsException(String.format("Платежное поручение с id = %s не найдено", request.getId()));
        }
        Worker worker = workerRepository.read(request.getWorkerId());
        if (worker == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", request.getWorkerId()));
        }
        PaymentOrder paymentOrder = paymentOrderMapper.RequestToPaymentOrder(request);
        paymentOrder.setWorker(worker);
        paymentOrderRepository.update(paymentOrder);
        return paymentOrderMapper.paymentOrderToResponse(paymentOrder);
    }

    // Удаление записи по запросу от API
    public PaymentOrderResponse paymentOrderDelete(Integer id) {
        PaymentOrder paymentOrder = paymentOrderRepository.read(id);
        if (paymentOrder == null) {
            throw new RecordNotExistsException(String.format("Платежное поручение с id = %s не найдено", id));
        }
        PaymentOrderResponse response = paymentOrderMapper.paymentOrderToResponse(paymentOrder);
        paymentOrderRepository.delete(id);
        return response;
    }

}
