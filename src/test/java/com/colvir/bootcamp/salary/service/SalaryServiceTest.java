package com.colvir.bootcamp.salary.service;

import com.colvir.bootcamp.salary.dto.*;
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

import java.sql.Date;
import java.util.ArrayList;
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
    private final Department department3Cre = new Department(3, "Dep 3 cre", null);
    private final Department department1Upd = new Department(1, "Dep 1 upd", null);
    private final DepartmentCreateRequest departmentCreateRequest3 = new DepartmentCreateRequest("Dep 3 cre");
    private final DepartmentUpdateRequest departmentUpdateRequest1 = new DepartmentUpdateRequest(1, "Dep 1 upd");
    private final DepartmentUpdateRequest departmentUpdateRequest3 = new DepartmentUpdateRequest(3, "Dep 3 upd");
    private final List<Department> departments = new ArrayList<>();
    private DepartmentListResponse departmentResponseList = null;

    // Работник: Тестовые данные
    private final Worker worker1 = new Worker(1, "Thomas Anderson", 100F, department1, null);
    private final Worker worker2 = new Worker(2, "John Connor", 200F, department1, null);
    private final Worker worker3Cre = new Worker(3, "Lara Croft cre", 300F, department1, null);
    private final Worker worker1Upd = new Worker(1, "Thomas Anderson upd", 500F, department1, null);
    private final WorkerCreateRequest workerCreateRequest3 = new WorkerCreateRequest(1, "Lara Croft cre", 300F);
    private final WorkerUpdateRequest workerUpdateRequest1 = new WorkerUpdateRequest(1, 1, "Thomas Anderson upd", 500F);
    private final WorkerUpdateRequest workerUpdateRequest3 = new WorkerUpdateRequest(3, 1, "Lara Croft upd", 300F);
    private final List<Worker> workers = new ArrayList<>();
    private WorkerListResponse workerResponseList = null;

    // Платежное поручение: Тестовые данные
    private final PaymentOrder payment1 = new PaymentOrder(1, new Date(2024, 03, 15), 15F, worker1);
    private final PaymentOrder payment2 = new PaymentOrder(2, new Date(2024, 03, 15), 20F, worker1);
    private final PaymentOrder payment3Cre = new PaymentOrder(3, new Date(2024, 03, 15), 30F, worker1);
    private final PaymentOrder payment1Upd = new PaymentOrder(1, new Date(2024, 03, 16), 100F, worker1);
    private final PaymentOrderCreateRequest paymentCreateRequest3 = new PaymentOrderCreateRequest(1, new Date(2024, 03, 15), 30F);
    private final PaymentOrderUpdateRequest paymentUpdateRequest1 = new PaymentOrderUpdateRequest(1, 1, new Date(2024, 03, 16), 100F);
    private final PaymentOrderUpdateRequest paymentUpdateRequest3 = new PaymentOrderUpdateRequest(3, 1, new Date(2024, 03, 15), 40F);
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
        when(departmentRepository.save(any())).thenReturn(department3Cre);
        when(departmentRepository.getById(1)).thenReturn(department1);
        when(departmentRepository.getById(2)).thenReturn(department2);
        when(departmentRepository.getById(3)).thenReturn(null);
        when(departmentRepository.getAll()).thenReturn(departments);
        when(departmentRepository.update(department1Upd)).thenReturn(department1Upd);
        when(departmentRepository.delete(1)).thenReturn(department1);

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
        when(workerRepository.save(any())).thenReturn(worker3Cre);
        when(workerRepository.getById(1)).thenReturn(worker1);
        when(workerRepository.getById(2)).thenReturn(worker2);
        when(workerRepository.getById(3)).thenReturn(null);
        when(workerRepository.getAll()).thenReturn(workers);
        when(workerRepository.update(worker1Upd)).thenReturn(worker1Upd);
        when(workerRepository.delete(1)).thenReturn(worker1);

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
        when(paymentOrderRepository.save(any())).thenReturn(payment3Cre);
        when(paymentOrderRepository.getById(1)).thenReturn(payment1);
        when(paymentOrderRepository.getById(2)).thenReturn(payment2);
        when(paymentOrderRepository.getById(3)).thenReturn(null);
        when(paymentOrderRepository.getAll()).thenReturn(payments);
        when(paymentOrderRepository.update(payment1Upd)).thenReturn(payment1Upd);
        when(paymentOrderRepository.delete(1)).thenReturn(payment1);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Подразделение

    @Test
    void departmentCreate_success() {
        // Подготовка данных
        PrepareData();
        DepartmentResponse expectedResponse = new DepartmentResponse(3,"Dep 3 cre");
        // Тест
        DepartmentResponse actualResponse = salaryService.departmentCreate(departmentCreateRequest3);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository, Mockito.times(1)).save(any());
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
        DepartmentResponse expectedResponse = new DepartmentResponse(1,"Dep 1 upd");
        // Тест
        DepartmentResponse actualResponse = salaryService.departmentUpdate(departmentUpdateRequest1);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository, Mockito.times(1)).update(any());
    }

    @Test
    void departmentUpdate_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.departmentUpdate(departmentUpdateRequest3));
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
        WorkerResponse expectedResponse = new WorkerResponse(3, 1, "Dep 1", "Lara Croft cre", 300F);
        // Тест
        WorkerResponse actualResponse = salaryService.workerCreate(workerCreateRequest3);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(workerRepository, Mockito.times(1)).save(any());
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
        WorkerResponse expectedResponse = new WorkerResponse(1, 1, "Dep 1", "Thomas Anderson upd", 500F);
        // Тест
        WorkerResponse actualResponse = salaryService.workerUpdate(workerUpdateRequest1);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(workerRepository, Mockito.times(1)).update(any());
    }

    @Test
    void workerUpdate_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.workerUpdate(workerUpdateRequest3));
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
        PaymentOrderResponse actualResponse = salaryService.paymentOrderCreate(paymentCreateRequest3);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(paymentOrderRepository, Mockito.times(1)).save(any());
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
        PaymentOrderResponse expectedResponse = new PaymentOrderResponse(1, 1, "Thomas Anderson", new Date(2024, 03, 16), 100F);
        // Тест
        PaymentOrderResponse actualResponse = salaryService.paymentOrderUpdate(paymentUpdateRequest1);
        // Проверка результата
        assertEquals(expectedResponse, actualResponse);
        verify(paymentOrderRepository, Mockito.times(1)).update(any());
    }

    @Test
    void paymentUpdate_exception() {
        // Подготовка данных
        PrepareData();
        // Тест
        assertThrows(RecordNotExistsException.class, () -> salaryService.paymentOrderUpdate(paymentUpdateRequest3));
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
