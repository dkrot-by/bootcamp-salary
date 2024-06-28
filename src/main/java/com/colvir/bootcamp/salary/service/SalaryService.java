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
import com.colvir.bootcamp.salary.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final DepartmentMapper departmentMapper;
    private final WorkerMapper workerMapper;
    private final PaymentOrderMapper paymentOrderMapper;
    private final DepartmentRepository departmentRepository;
    private final WorkerRepository workerRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final DepartmentCacheRepository departmentCacheRepository;
    private final WorkerCacheRepository workerCacheRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Подразделение

    // Создание записи по запросу от API
    public DepartmentResponse departmentCreate(DepartmentCreateRequest request) {
        Department department = departmentMapper.createRequestToDepartment(request);
        return departmentMapper.departmentToResponse(departmentRepository.save(department));
    }

    // Передача списка записей по запросу от API
    public DepartmentListResponse departmentGetAll() {
        return departmentMapper.departmentListToResponse(departmentRepository.findAll());
    }

    // Передача записи по запросу от API
    public DepartmentResponse departmentGetById(Integer id) {
        // Сначала поиск записи в кеше
        Optional<Department> department = departmentCacheRepository.findById(id);
        if (department.isEmpty()) {
            // Если нет записи в кеше, поиск записи в базе данных и запись ее в кеш
            department = Optional.ofNullable(departmentRepository.findById(id)
                    .orElseThrow(() -> new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", id))));
            department.ifPresent(departmentCacheRepository::save);
        }
        return departmentMapper.departmentToResponse(department.orElse(null));
    }

    // Обновление записи по запросу от API
    public DepartmentResponse departmentUpdate(DepartmentUpdateRequest request) {
        if (departmentRepository.findById(request.getId()).isEmpty()) {
            throw new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", request.getId()));
        }
        Department department = departmentMapper.updateRequestToDepartment(request);
        return departmentMapper.departmentToResponse(departmentRepository.save(department));
    }

    // Удаление записи по запросу от API
    public DepartmentResponse departmentDelete(Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", id)));
        if (!workerRepository.findAllByDepartment(department).isEmpty()) {
            throw new SpawnedRecordsException(String.format("Удаление не возможно: в подразделении с id = %s есть работники", id));
        }
        DepartmentResponse response = departmentMapper.departmentToResponse(department);
        departmentRepository.deleteById(id);
        return response;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Работник

    // Создание записи по запросу от API
    public WorkerResponse workerCreate(WorkerCreateRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", request.getDepartmentId())));
        Worker worker = workerMapper.createRequestToWorker(request);
        worker.setDepartment(department);
        return workerMapper.workerToResponse(workerRepository.save(worker));
    }

    // Передача списка записей по запросу от API
    public WorkerListResponse workerGetAll() {
        return workerMapper.workerListToResponse(workerRepository.findAll());
    }

    // Передача записи по запросу от API
    public WorkerResponse workerGetById(Integer id) {
       // Сначала поиск записи в кеше
        Optional<Worker> worker = workerCacheRepository.findById(id);
        if (worker.isEmpty()) {
            // Если нет записи в кеше, поиск записи в базе данных и запись ее в кеш
            worker = Optional.ofNullable(workerRepository.findById(id)
                    .orElseThrow(() -> new RecordNotExistsException(String.format("Работник с id = %s не найден", id))));
            worker.ifPresent(workerCacheRepository::save);
        }
        return workerMapper.workerToResponse((worker.orElse(null)));
    }

    // Обновление записи по запросу от API
    public WorkerResponse workerUpdate(WorkerUpdateRequest request) {
        if (workerRepository.findById(request.getId()).isEmpty()) {
            throw new RecordNotExistsException(String.format("Работник с id = %s не найден", request.getId()));
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RecordNotExistsException(String.format("Подразделение с id = %s не найдено", request.getDepartmentId())));
        Worker worker = workerMapper.updateRequestToWorker(request);
        worker.setDepartment(department);
        return workerMapper.workerToResponse(workerRepository.save(worker));
    }

    // Удаление записи по запросу от API
    public WorkerResponse workerDelete(Integer id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RecordNotExistsException(String.format("Работник с id = %s не найден", id)));
        if (!paymentOrderRepository.findAllByWorker(worker).isEmpty()) {
            throw new SpawnedRecordsException(String.format("Удаление не возможно: у работника с id = %s есть платежные поручения", id));
        }
        WorkerResponse response = workerMapper.workerToResponse(worker);
        workerRepository.deleteById(id);
        return response;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Платежное поручение

    // Создание записи по запросу от API
    public PaymentOrderResponse paymentOrderCreate(PaymentOrderCreateRequest request) {
        Worker worker = workerRepository.findById(request.getWorkerId())
                .orElseThrow(() -> new RecordNotExistsException(String.format("Работник с id = %s не найден", request.getWorkerId())));
        PaymentOrder paymentOrder = paymentOrderMapper.createRequestToPaymentOrder(request);
        paymentOrder.setWorker(worker);
        return paymentOrderMapper.paymentOrderToResponse(paymentOrderRepository.save(paymentOrder));
    }

    // Передача списка записей по запросу от API
    public PaymentOrderListResponse paymentOrderGetAll() {
        return paymentOrderMapper.paymentOrderListToResponse(paymentOrderRepository.findAll());
    }

    // Передача записи по запросу от API
    public PaymentOrderResponse paymentOrderGetById(Integer id) {
        PaymentOrder payment = paymentOrderRepository.findById(id)
                .orElseThrow(() -> new RecordNotExistsException(String.format("Платежное поручение с id = %s не найдено", id)));
        return paymentOrderMapper.paymentOrderToResponse(payment);
    }

    // Обновление записи по запросу от API
    public PaymentOrderResponse paymentOrderUpdate(PaymentOrderUpdateRequest request) {
        if (paymentOrderRepository.findById(request.getId()).isEmpty()) {
            throw new RecordNotExistsException(String.format("Платежное поручение с id = %s не найдено", request.getId()));
        }
        Worker worker = workerRepository.findById(request.getWorkerId())
                .orElseThrow(() -> new RecordNotExistsException(String.format("Работник с id = %s не найден", request.getWorkerId())));
        PaymentOrder paymentOrder = paymentOrderMapper.updateRequestToPaymentOrder(request);
        paymentOrder.setWorker(worker);
        return paymentOrderMapper.paymentOrderToResponse(paymentOrderRepository.save(paymentOrder));
    }

    // Удаление записи по запросу от API
    public PaymentOrderResponse paymentOrderDelete(Integer id) {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id)
                .orElseThrow(() -> new RecordNotExistsException(String.format("Платежное поручение с id = %s не найдено", id)));
        PaymentOrderResponse response = paymentOrderMapper.paymentOrderToResponse(paymentOrder);
        paymentOrderRepository.deleteById(id);
        return response;
    }

}
