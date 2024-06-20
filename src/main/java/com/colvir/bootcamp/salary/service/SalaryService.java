package com.colvir.bootcamp.salary.service;

import com.colvir.bootcamp.salary.dto.*;
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
    public DepartmentResponse departmentCreate(DepartmentCreateRequest request) {
        Department department = departmentMapper.createRequestToDepartment(request);
        return departmentMapper.departmentToResponse(departmentRepository.save(department));
    }

    // Передача списка записей по запросу от API
    public DepartmentListResponse departmentGetAll() {
        return departmentMapper.departmentListToResponse(departmentRepository.getAll());
    }

    // Передача записи по запросу от API
    public DepartmentResponse departmentGetById(Integer id) {
        Department department = departmentRepository.getById(id);
        if (department == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", id));
        }
        return departmentMapper.departmentToResponse(department);
    }

    // Обновление записи по запросу от API
    public DepartmentResponse departmentUpdate(DepartmentUpdateRequest request) {
        if (departmentRepository.getById(request.getId()) == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", request.getId()));
        }
        Department department = departmentMapper.updateRequestToDepartment(request);
        Department departmentResult = departmentRepository.update(department);
        return departmentMapper.departmentToResponse(departmentResult);
    }

    // Удаление записи по запросу от API
    public DepartmentResponse departmentDelete(Integer id) {
        Department department = departmentRepository.getById(id);
        if (department == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", id));
        }
        if (!workerRepository.getByDepartment(department).isEmpty()) {
            throw new SpawnedRecordsException(String.format("Удаление не возможно: в подразделении с id = %s есть работники", id));
        }
        DepartmentResponse response = departmentMapper.departmentToResponse(department);
        departmentRepository.delete(id);
        return response;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Работник

    // Создание записи по запросу от API
    public WorkerResponse workerCreate(WorkerCreateRequest request) {
        Department department = departmentRepository.getById(request.getDepartmentId());
        if (department == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", request.getDepartmentId()));
        }
        Worker worker = workerMapper.createRequestToWorker(request);
        worker.setDepartment(department);
        return workerMapper.workerToResponse(workerRepository.save(worker));
    }

    // Передача списка записей по запросу от API
    public WorkerListResponse workerGetAll() {
        return workerMapper.workerListToResponse(workerRepository.getAll());
    }

    // Передача записи по запросу от API
    public WorkerResponse workerGetById(Integer id) {
        Worker worker = workerRepository.getById(id);
        if (worker == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", id));
        }
        return workerMapper.workerToResponse(worker);
    }

    // Обновление записи по запросу от API
    public WorkerResponse workerUpdate(WorkerUpdateRequest request) {
        if (workerRepository.getById(request.getId()) == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", request.getId()));
        }
        Department department = departmentRepository.getById(request.getDepartmentId());
        if (department == null) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", request.getDepartmentId()));
        }
        Worker worker = workerMapper.updateRequestToWorker(request);
        worker.setDepartment(department);
        workerRepository.update(worker);
        return workerMapper.workerToResponse(worker);
    }

    // Удаление записи по запросу от API
    public WorkerResponse workerDelete(Integer id) {
        Worker worker = workerRepository.getById(id);
        if (worker == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", id));
        }
        if (!paymentOrderRepository.getByWorker(worker).isEmpty()) {
            throw new SpawnedRecordsException(String.format("Удаление не возможно: у работника с id = %s есть платежные поручения", id));
        }
        WorkerResponse response = workerMapper.workerToResponse(worker);
        workerRepository.delete(id);
        return response;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Платежное поручение

    // Создание записи по запросу от API
    public PaymentOrderResponse paymentOrderCreate(PaymentOrderCreateRequest request) {
        Worker worker = workerRepository.getById(request.getWorkerId());
        if (worker == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", request.getWorkerId()));
        }
        PaymentOrder paymentOrder = paymentOrderMapper.createRequestToPaymentOrder(request);
        paymentOrder.setWorker(worker);
        return paymentOrderMapper.paymentOrderToResponse(paymentOrderRepository.save(paymentOrder));
    }

    // Передача списка записей по запросу от API
    public PaymentOrderListResponse paymentOrderGetAll() {
        return paymentOrderMapper.paymentOrderListToResponse(paymentOrderRepository.getAll());
    }

    // Передача записи по запросу от API
    public PaymentOrderResponse paymentOrderGetById(Integer id) {
        PaymentOrder payment = paymentOrderRepository.getById(id);
        if (payment == null) {
            throw new RecordNotExistsException(String.format("Платежное поручение с id = %s не найдено", id));
        }
        return paymentOrderMapper.paymentOrderToResponse(payment);
    }

    // Обновление записи по запросу от API
    public PaymentOrderResponse paymentOrderUpdate(PaymentOrderUpdateRequest request) {
        if (paymentOrderRepository.getById(request.getId()) == null) {
            throw new RecordNotExistsException(String.format("Платежное поручение с id = %s не найдено", request.getId()));
        }
        Worker worker = workerRepository.getById(request.getWorkerId());
        if (worker == null) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", request.getWorkerId()));
        }
        PaymentOrder paymentOrder = paymentOrderMapper.updateRequestToPaymentOrder(request);
        paymentOrder.setWorker(worker);
        paymentOrderRepository.update(paymentOrder);
        return paymentOrderMapper.paymentOrderToResponse(paymentOrder);
    }

    // Удаление записи по запросу от API
    public PaymentOrderResponse paymentOrderDelete(Integer id) {
        PaymentOrder paymentOrder = paymentOrderRepository.getById(id);
        if (paymentOrder == null) {
            throw new RecordNotExistsException(String.format("Платежное поручение с id = %s не найдено", id));
        }
        PaymentOrderResponse response = paymentOrderMapper.paymentOrderToResponse(paymentOrder);
        paymentOrderRepository.delete(id);
        return response;
    }

}
