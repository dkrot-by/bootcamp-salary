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

import java.util.Arrays;

import static com.colvir.bootcamp.salary.model.InternalErrorStatus.RECORD_DOES_NOT_EXISTS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

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

    private static final String URL_TEMPLATE = "/salary/dep";
    private static final String URL_TEMPLATE_BY_ID = "/salary/dep/{id}";
    private static final Integer ID = 1;

    private final DepartmentCreateRequest createRequest = new DepartmentCreateRequest("Dep 1");
    private final DepartmentUpdateRequest updateRequest = new DepartmentUpdateRequest(1, "Dep 1");
    private final DepartmentResponse response = new DepartmentResponse(1, "Dep 1");
    private final DepartmentListResponse listResponse = new DepartmentListResponse(Arrays.asList(
            new DepartmentResponse(1, "Dep 1"),
            new DepartmentResponse(2, "Dep 2")));
    private final ErrorResponse notExistsResponse = new ErrorResponse(RECORD_DOES_NOT_EXISTS, "Подразделение с id = 2 не найдено");

    // Создание: успешно
    @Test
    @WithMockUser
    void departmentCreate_success() throws Exception {
        when(salaryService.departmentCreate(createRequest)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL_TEMPLATE)
                        .content(objectMapper.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).departmentCreate(createRequest);
        verifyNoMoreInteractions(salaryService);
    }

    // Чтение: успешно
    @Test
    @WithMockUser
    void departmentGetById_success() throws Exception {
        when(salaryService.departmentGetById(ID)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).departmentGetById(ID);
        verifyNoMoreInteractions(salaryService);
    }

    // Чтение: ошибка
    @Test
    @WithMockUser
    void departmentGetById_exception() throws Exception {
        when(salaryService.departmentGetById(ID)).thenThrow(new RecordNotExistsException(notExistsResponse.getMessage()));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(notExistsResponse)));
        verify(salaryService, times(1)).departmentGetById(ID);
        verifyNoMoreInteractions(salaryService);
    }

    // Чтение всех записей: успешно
    @Test
    @WithMockUser
    void departmentGetAll_success() throws Exception {
        when(salaryService.departmentGetAll()).thenReturn(listResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL_TEMPLATE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listResponse)));
        verify(salaryService, times(1)).departmentGetAll();
        verifyNoMoreInteractions(salaryService);
    }

    // Изменение: успешно
    @Test
    @WithMockUser
    void departmentUpdate_success() throws Exception {
        when(salaryService.departmentUpdate(updateRequest)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL_TEMPLATE)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).departmentUpdate(updateRequest);
        verifyNoMoreInteractions(salaryService);
    }

    // Изменение: ошибка
    @Test
    @WithMockUser
    void departmentUpdate_exception() throws Exception {
        when(salaryService.departmentUpdate(updateRequest)).thenThrow(new RecordNotExistsException(notExistsResponse.getMessage()));
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL_TEMPLATE)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(notExistsResponse)));
        verify(salaryService, times(1)).departmentUpdate(updateRequest);
        verifyNoMoreInteractions(salaryService);
    }

    // Удаление: успешно
    @Test
    @WithMockUser(roles = "ADMIN")
    void departmentDelete_success() throws Exception {
        when(salaryService.departmentDelete(ID)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(salaryService, times(1)).departmentDelete(ID);
        verifyNoMoreInteractions(salaryService);
    }

    // Удаление: ошибка
    @Test
    @WithMockUser(roles = "ADMIN")
    void departmentDelete_exception() throws Exception {
        when(salaryService.departmentDelete(ID)).thenThrow(new RecordNotExistsException(notExistsResponse.getMessage()));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL_TEMPLATE_BY_ID, ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(notExistsResponse)));
        verify(salaryService, times(1)).departmentDelete(ID);
        verifyNoMoreInteractions(salaryService);
    }

}