package com.colvir.bootcamp.salary.service;

import com.colvir.bootcamp.salary.dto.*;
import com.colvir.bootcamp.salary.exception.RecordExistsException;
import com.colvir.bootcamp.salary.exception.RecordNotExistsException;
import com.colvir.bootcamp.salary.mapper.DepartmentMapperImpl;
import com.colvir.bootcamp.salary.mapper.PaymentOrderMapperImpl;
import com.colvir.bootcamp.salary.mapper.WorkerMapperImpl;
import com.colvir.bootcamp.salary.model.Department;
import com.colvir.bootcamp.salary.model.PaymentOrder;
import com.colvir.bootcamp.salary.model.Worker;
import com.colvir.bootcamp.salary.repository.DepartmentRepository;
import com.colvir.bootcamp.salary.repository.PaymentOrderRepository;
import com.colvir.bootcamp.salary.repository.WorkerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SalaryService.class,
        DepartmentMapperImpl.class,
        WorkerMapperImpl.class,
        PaymentOrderMapperImpl.class
})

public class SalaryServiceTest {

    @Autowired
    private SalaryService salaryService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private WorkerRepository workerRepository;

    @MockBean
    private PaymentOrderRepository paymentOrderRepository;

    // Подразделение: Тестовые данные
    private final Department department1 = new Department(1, "Dep 1", null);
    private final Department department2 = new Department(2, "Dep 2", null);
    private final DepartmentRequest departmentRequest1 = new DepartmentRequest(1, "Dep 1");
    private final DepartmentRequest departmentRequest2 = new DepartmentRequest(1, "Dep 1");
    private final DepartmentRequest departmentRequest3 = new DepartmentRequest(3, "Dep 3");
    private final List<Department> departments = new ArrayList<>();
    private DepartmentListResponse departmentResponseList = null;

    // Работник: Тестовые данные
    private final Worker worker1 = new Worker(1, "Thomas Anderson", 100F, department1, null);
    private final Worker worker2 = new Worker(2, "John Connor", 200F, department1, null);
    private final WorkerRequest workerRequest1 = new WorkerRequest(1, 1, "Thomas Anderson", 100F);
    private final WorkerRequest workerRequest2 = new WorkerRequest(2, 1, "John Connor", 200F);
    private final WorkerRequest workerRequest3 = new WorkerRequest(3, 1, "Lara Croft", 300F);
    private final List<Worker> workers = new ArrayList<>();
    private WorkerListResponse workerResponseList = null;

    // Платежное поручение: Тестовые данные
    private final PaymentOrder payment1 = new PaymentOrder(1, new Date(2024, 03, 15), 15F, worker1);
    private final PaymentOrder payment2 = new PaymentOrder(2, new Date(2024, 03, 15), 20F, worker1);
    private final PaymentOrderRequest paymentRequest1 = new PaymentOrderRequest(1, 1, new Date(2024, 03, 15), 15F);
    private final PaymentOrderRequest paymentRequest2 = new PaymentOrderRequest(2, 1, new Date(2024, 03, 15), 20F);
    private final PaymentOrderRequest paymentRequest3 = new PaymentOrderRequest(3, 1, new Date(2024, 03, 15), 30F);
    private final List<PaymentOrder> payments = new ArrayList<>();
    private PaymentOrderListResponse paymentResponseList = null;

    // Подготовка данных, заглушки для репозитория
    private void PrepareData() {
        // Подразделение
        if (departments.isEmpty()) {
            departments.add(department1);
            departments.add(department2);
        }
        if (departmentResponseList == null) {
            List<DepartmentResponse> departmentResponses = new ArrayList<>();
            departmentResponses.add(new DepartmentResponse(1, "Dep 1"));
            departmentResponses.add(new DepartmentResponse(2, "Dep 2"));
            departmentResponseList = new DepartmentListResponse(departmentResponses);
        }
        doNothing().when(departmentRepository).save(any());
        when(departmentRepository.getById(1)).thenReturn(department1);
        when(departmentRepository.getById(2)).thenReturn(department2);
        when(departmentRepository.getById(3)).thenReturn(null);
        when(departmentRepository.getAll()).thenReturn(departments);
        doNothing().when(departmentRepository).update(any());
        doNothing().when(departmentRepository).delete(any());

        // Работник
        if (workers.isEmpty()) {
            workers.add(worker1);
            workers.add(worker2);
        }
        if (workerResponseList == null) {
            List<WorkerResponse> workerResponses = new ArrayList<>();
            workerResponses.add(new WorkerResponse(1, 1, "Dep 1", "Thomas Anderson", 100F));
            workerResponses.add(new WorkerResponse(2, 1, "Dep 1", "John Connor", 200F));
            workerResponseList = new WorkerListResponse(workerResponses);
        }
        doNothing().when(workerRepository).save(any());
        when(workerRepository.getById(1)).thenReturn(worker1);
        when(workerRepository.getById(2)).thenReturn(worker2);
        when(workerRepository.getById(3)).thenReturn(null);
        when(workerRepository.getAll()).thenReturn(workers);
        doNothing().when(workerRepository).update(any());
        doNothing().when(workerRepository).delete(any());

        // Платежное поручение
        if (payments.isEmpty()) {
            payments.add(payment1);
            payments.add(payment2);
        }
        if (paymentResponseList == null) {
            List<PaymentOrderResponse> paymentResponses = new ArrayList<>();
            paymentResponses.add(new PaymentOrderResponse(1, 1, "Thomas Anderson", new Date(2024, 03, 15), 15F));
            paymentResponses.add(new PaymentOrderResponse(2, 1, "Thomas Anderson", new Date(2024, 03, 15), 20F));
            paymentResponseList = new PaymentOrderListResponse(paymentResponses);
        }
        doNothing().when(paymentOrderRepository).save(any());
        when(paymentOrderRepository.getById(1)).thenReturn(payment1);
        when(paymentOrderRepository.getById(2)).thenReturn(payment2);
        when(paymentOrderRepository.getById(3)).thenReturn(null);
        when(paymentOrderRepository.getAll()).thenReturn(payments);
        doNothing().when(paymentOrderRepository).update(any());
        doNothing().when(paymentOrderRepository).delete(any());
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Подразделение

    @Test
    void departmentCreate_success() {
        // Подготовка данных
        PrepareData();
        DepartmentResponse expectedResponse = new DepartmentResponse(3,"Dep 3");
        // Тест
        DepartmentResponse actualResponse = salaryService.departmentCreate(departmentRequest3);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository, Mockito.times(1)).save(any());
    }

    @Test
    void departmentCreate_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordExistsException.class, () -> salaryService.departmentCreate(departmentRequest1));
        // Проверка результата
        verify(departmentRepository, Mockito.times(0)).save(any());
    }

    @Test
    void departmentRead_success() {
        // Подготовка данных
        PrepareData();
        DepartmentResponse expectedResponse = new DepartmentResponse(1,"Dep 1");
        // Тест
        DepartmentResponse actualResponse = salaryService.departmentGetById(1);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void departmentReadList_success() {
        // Подготовка данных
        PrepareData();
        // Тест
        DepartmentListResponse actualResponse = salaryService.departmentGetAll();
        // Проверка результата
        assertEquals(departmentResponseList, actualResponse);
    }

    @Test
    void departmentRead_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.departmentGetById(3));
    }

    @Test
    void departmentUpdate_success() {
        // Подготовка данных
        PrepareData();
        DepartmentRequest request = new DepartmentRequest(1, "Dep 1 upd");
        DepartmentResponse expectedResponse = new DepartmentResponse(1,"Dep 1 upd");
        // Тест
        DepartmentResponse actualResponse = salaryService.departmentUpdate(request);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository, Mockito.times(1)).update(any());
    }

    @Test
    void departmentUpdate_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.departmentUpdate(departmentRequest3));
        // Проверка результата
        verify(departmentRepository, Mockito.times(0)).update(any());
    }

    @Test
    void departmentDelete_success() {
        // Подготовка данных
        PrepareData();
        DepartmentResponse expectedResponse = new DepartmentResponse(1,"Dep 1");
        // Тест
        DepartmentResponse actualResponse = salaryService.departmentDelete(1);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository, Mockito.times(1)).delete(any());
    }

    @Test
    void departmentDelete_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.departmentDelete(3));
        // Проверка результата
        verify(departmentRepository, Mockito.times(0)).delete(any());
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Работник

    @Test
    void workerCreate_success() {
        // Подготовка данных
        PrepareData();
        WorkerResponse expectedResponse = new WorkerResponse(3, 1, "Dep 1", "Lara Croft", 300F);
        // Тест
        WorkerResponse actualResponse = salaryService.workerCreate(workerRequest3);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(workerRepository, Mockito.times(1)).save(any());
    }

    @Test
    void workerCreate_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordExistsException.class, () -> salaryService.workerCreate(workerRequest1));
        // Проверка результата
        verify(workerRepository, Mockito.times(0)).save(any());
    }

    @Test
    void workerRead_success() {
        // Подготовка данных
        PrepareData();
        WorkerResponse expectedResponse = new WorkerResponse(1, 1, "Dep 1", "Thomas Anderson", 100F);
        // Тест
        WorkerResponse actualResponse = salaryService.workerGetById(1);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void workerReadList_success() {
        // Подготовка данных
        PrepareData();
        // Тест
        WorkerListResponse actualResponse = salaryService.workerGetAll();
        // Проверка результата
        assertEquals(workerResponseList, actualResponse);
    }

    @Test
    void workerRead_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.workerGetById(3));
    }

    @Test
    void workerUpdate_success() {
        // Подготовка данных
        PrepareData();
        WorkerRequest request = new WorkerRequest(1, 1, "New name", 350F);
        WorkerResponse expectedResponse = new WorkerResponse(1, 1, "Dep 1", "New name", 350F);
        // Тест
        WorkerResponse actualResponse = salaryService.workerUpdate(request);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(workerRepository, Mockito.times(1)).update(any());
    }

    @Test
    void workerUpdate_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.workerUpdate(workerRequest3));
        // Проверка результата
        verify(workerRepository, Mockito.times(0)).update(any());
    }

    @Test
    void workerDelete_success() {
        // Подготовка данных
        PrepareData();
        WorkerResponse expectedResponse = new WorkerResponse(1, 1, "Dep 1", "Thomas Anderson", 100F);
        // Тест
        WorkerResponse actualResponse = salaryService.workerDelete(1);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(workerRepository, Mockito.times(1)).delete(any());
    }

    @Test
    void workerDelete_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.workerDelete(3));
        // Проверка результата
        verify(workerRepository, Mockito.times(0)).delete(any());
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Платежное поручение

    @Test
    void paymentCreate_success() {
        // Подготовка данных
        PrepareData();
        PaymentOrderResponse expectedResponse = new PaymentOrderResponse(3, 1, "Thomas Anderson", new Date(2024, 03, 15), 30F);
        // Тест
        PaymentOrderResponse actualResponse = salaryService.paymentOrderCreate(paymentRequest3);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(paymentOrderRepository, Mockito.times(1)).save(any());
    }

    @Test
    void paymentCreate_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordExistsException.class, () -> salaryService.paymentOrderCreate(paymentRequest1));
        // Проверка результата
        verify(paymentOrderRepository, Mockito.times(0)).save(any());
    }

    @Test
    void paymentRead_success() {
        // Подготовка данных
        PrepareData();
        PaymentOrderResponse expectedResponse = new PaymentOrderResponse(1, 1, "Thomas Anderson", new Date(2024, 03, 15), 15F);
        // Тест
        PaymentOrderResponse actualResponse = salaryService.paymentOrderGetById(1);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void paymentReadList_success() {
        // Подготовка данных
        PrepareData();
        // Тест
        PaymentOrderListResponse actualResponse = salaryService.paymentOrderGetAll();
        // Проверка результата
        assertEquals(paymentResponseList, actualResponse);
    }

    @Test
    void paymentRead_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.paymentOrderGetById(3));
    }

    @Test
    void paymentUpdate_success() {
        // Подготовка данных
        PrepareData();
        PaymentOrderRequest request = new PaymentOrderRequest(1, 1, new Date(2024, 03, 20), 500F);
        PaymentOrderResponse expectedResponse = new PaymentOrderResponse(1, 1, "Thomas Anderson", new Date(2024, 03, 20), 500F);
        // Тест
        PaymentOrderResponse actualResponse = salaryService.paymentOrderUpdate(request);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(paymentOrderRepository, Mockito.times(1)).update(any());
    }

    @Test
    void paymentUpdate_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.paymentOrderUpdate(paymentRequest3));
        // Проверка результата
        verify(paymentOrderRepository, Mockito.times(0)).update(any());
    }

    @Test
    void paymentDelete_success() {
        // Подготовка данных
        PrepareData();
        PaymentOrderResponse expectedResponse = new PaymentOrderResponse(1, 1, "Thomas Anderson", new Date(2024, 03, 15), 15F);
        // Тест
        PaymentOrderResponse actualResponse = salaryService.paymentOrderDelete(1);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(paymentOrderRepository, Mockito.times(1)).delete(any());
    }

    @Test
    void paymentDelete_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.paymentOrderDelete(3));
        // Проверка результата
        verify(paymentOrderRepository, Mockito.times(0)).delete(any());
    }

}
