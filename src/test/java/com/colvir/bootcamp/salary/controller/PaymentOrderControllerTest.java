package com.colvir.bootcamp.salary.controller;

import com.colvir.bootcamp.salary.dto.*;
import com.colvir.bootcamp.salary.exception.RecordNotExistsException;
import com.colvir.bootcamp.salary.service.SalaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import static com.colvir.bootcamp.salary.model.InternalErrorStatus.RECORD_DOES_NOT_EXISTS;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentOrderController.class)
public class PaymentOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @MockBean
    private SalaryService salaryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String URL_TEMPLATE = "/salary/payment";
    private static final String URL_TEMPLATE_BY_ID = "/salary/payment/{id}";
    private static final Integer ID = 1;

    private final PaymentOrderCreateRequest createRequest = new PaymentOrderCreateRequest(40, Date.valueOf(LocalDate.of(2024, 3, 15)), 4.56F);
    private final PaymentOrderUpdateRequest updateRequest = new PaymentOrderUpdateRequest(1, 40, Date.valueOf(LocalDate.of(2024, 3, 15)), 4.56F);
    private final PaymentOrderResponse response = new PaymentOrderResponse(1, 40, "Thomas Anderson", Date.valueOf(LocalDate.of(2024, 3, 15)), 4.56F);
    private final PaymentOrderListResponse listResponse = new PaymentOrderListResponse(Arrays.asList(
            new PaymentOrderResponse(1, 40, "Thomas Anderson", Date.valueOf(LocalDate.of(2024, 3, 15)), 4.56F),
            new PaymentOrderResponse(2, 40, "John Connor", Date.valueOf(LocalDate.of(2024, 3, 16)), 4.57F)));
    private final ErrorResponse notExistsResponse = new ErrorResponse(RECORD_DOES_NOT_EXISTS, "Платежное поручение с id = 2 не найдено");

    // Создание: успешно
    @Test
    @WithMockUser
    void paymentOrderCreate_success() throws Exception {
        when(salaryService.paymentOrderCreate(createRequest)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL_TEMPLATE)
                        .content(objectMapper.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).paymentOrderCreate(createRequest);
        verifyNoMoreInteractions(salaryService);
    }

    // Чтение: успешно
    @Test
    @WithMockUser
    void paymentOrderGetById_success() throws Exception {
        when(salaryService.paymentOrderGetById(ID)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).paymentOrderGetById(ID);
        verifyNoMoreInteractions(salaryService);
    }

    // Чтение: ошибка
    @Test
    @WithMockUser
    void paymentOrderGetById_exception() throws Exception {
        when(salaryService.paymentOrderGetById(ID)).thenThrow(new RecordNotExistsException(notExistsResponse.getMessage()));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(notExistsResponse)));
        verify(salaryService, times(1)).paymentOrderGetById(ID);
        verifyNoMoreInteractions(salaryService);
    }

    // Чтение всех записей: успешно
    @Test
    @WithMockUser
    void paymentOrderGetAll_success() throws Exception {
        when(salaryService.paymentOrderGetAll()).thenReturn(listResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL_TEMPLATE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listResponse)));
        verify(salaryService, times(1)).paymentOrderGetAll();
        verifyNoMoreInteractions(salaryService);
    }

    // Изменение: успешно
    @Test
    @WithMockUser
    void paymentOrderUpdate_success() throws Exception {
        when(salaryService.paymentOrderUpdate(updateRequest)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL_TEMPLATE)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).paymentOrderUpdate(updateRequest);
        verifyNoMoreInteractions(salaryService);
    }

    // Изменение: ошибка
    @Test
    @WithMockUser
    void paymentOrderUpdate_exception() throws Exception {
        when(salaryService.paymentOrderUpdate(updateRequest)).thenThrow(new RecordNotExistsException(notExistsResponse.getMessage()));
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL_TEMPLATE)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(notExistsResponse)));
        verify(salaryService, times(1)).paymentOrderUpdate(updateRequest);
        verifyNoMoreInteractions(salaryService);
    }

    // Удаление: успешно
    @Test
    @WithMockUser
    void paymentOrderDelete_success() throws Exception {
        when(salaryService.paymentOrderDelete(ID)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).paymentOrderDelete(ID);
        verifyNoMoreInteractions(salaryService);
    }

    // Удаление: ошибка
    @Test
    @WithMockUser
    void paymentOrderDelete_exception() throws Exception {
        when(salaryService.paymentOrderDelete(ID)).thenThrow(new RecordNotExistsException(notExistsResponse.getMessage()));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(notExistsResponse)));
        verify(salaryService, times(1)).paymentOrderDelete(ID);
        verifyNoMoreInteractions(salaryService);
    }
    
}
