package com.colvir.bootcamp.salary.controller;

import com.colvir.bootcamp.salary.dto.*;
import com.colvir.bootcamp.salary.exception.RecordNotExistsException;
import com.colvir.bootcamp.salary.service.SalaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static com.colvir.bootcamp.salary.model.InternalErrorStatus.RECORD_DOES_NOT_EXISTS;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkerController.class)
//TODO: Не работает аннотация @WithMockUser, поэтому пока отключаю фильтры для тестов, а вместе с ними сносятся права
@AutoConfigureMockMvc(addFilters = false)
public class WorkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaryService salaryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String URL_TEMPLATE = "/salary/worker";
    private static final String URL_TEMPLATE_BY_ID = "/salary/worker/{id}";
    private static final Integer ID = 1;

    private final WorkerCreateRequest createRequest = new WorkerCreateRequest(200, "Thomas Anderson", 1.23F);
    private final WorkerUpdateRequest updateRequest = new WorkerUpdateRequest(1, 200, "Thomas Anderson", 1.23F);
    private final WorkerResponse response = new WorkerResponse(1, 200, "Dep 1", "Thomas Anderson", 1.23F);
    private final WorkerListResponse listResponse = new WorkerListResponse(Arrays.asList(
            new WorkerResponse(1, 200, "Dep 1", "Thomas Anderson", 1.23F),
            new WorkerResponse(2, 200, "Dep 1", "John Connor", 1.23F)));
    private final ErrorResponse notExistsResponse = new ErrorResponse(RECORD_DOES_NOT_EXISTS, "Работник с id = 2 не найден");

    // Создание: успешно
    @Test
    @WithMockUser
    void workerCreate_success() throws Exception {
        when(salaryService.workerCreate(createRequest)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL_TEMPLATE)
                        .content(objectMapper.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).workerCreate(createRequest);
        verifyNoMoreInteractions(salaryService);
    }

    // Чтение: успешно
    @Test
    @WithMockUser
    void workerGetById_success() throws Exception {
        when(salaryService.workerGetById(ID)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).workerGetById(ID);
        verifyNoMoreInteractions(salaryService);
    }

    // Чтение: ошибка
    @Test
    @WithMockUser
    void workerGetById_exception() throws Exception {
        when(salaryService.workerGetById(ID)).thenThrow(new RecordNotExistsException(notExistsResponse.getMessage()));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(notExistsResponse)));
        verify(salaryService, times(1)).workerGetById(ID);
        verifyNoMoreInteractions(salaryService);
    }

    // Чтение всех записей: успешно
    @Test
    @WithMockUser
    void workerGetAll_success() throws Exception {
        when(salaryService.workerGetAll()).thenReturn(listResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL_TEMPLATE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listResponse)));
        verify(salaryService, times(1)).workerGetAll();
        verifyNoMoreInteractions(salaryService);
    }

    // Изменение: успешно
    @Test
    @WithMockUser
    void workerUpdate_success() throws Exception {
        when(salaryService.workerUpdate(updateRequest)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL_TEMPLATE)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).workerUpdate(updateRequest);
        verifyNoMoreInteractions(salaryService);
    }

    // Изменение: ошибка
    @Test
    @WithMockUser
    void workerUpdate_exception() throws Exception {
        when(salaryService.workerUpdate(updateRequest)).thenThrow(new RecordNotExistsException(notExistsResponse.getMessage()));
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL_TEMPLATE)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(notExistsResponse)));
        verify(salaryService, times(1)).workerUpdate(updateRequest);
        verifyNoMoreInteractions(salaryService);
    }

    // Удаление: успешно
    @Test
    @WithMockUser
    void workerDelete_success() throws Exception {
        when(salaryService.workerDelete(ID)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).workerDelete(ID);
        verifyNoMoreInteractions(salaryService);
    }

    // Удаление: ошибка
    @Test
    @WithMockUser
    void workerDelete_exception() throws Exception {
        when(salaryService.workerDelete(ID)).thenThrow(new RecordNotExistsException(notExistsResponse.getMessage()));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(notExistsResponse)));
        verify(salaryService, times(1)).workerDelete(ID);
        verifyNoMoreInteractions(salaryService);
    }
    
}
