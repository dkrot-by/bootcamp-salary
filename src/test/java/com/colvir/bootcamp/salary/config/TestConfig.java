package com.colvir.bootcamp.salary.config;

import com.colvir.bootcamp.salary.mapper.DepartmentMapper;
import com.colvir.bootcamp.salary.mapper.PaymentOrderMapper;
import com.colvir.bootcamp.salary.mapper.WorkerMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public DepartmentMapper getDepartmentMapper() {
        return Mappers.getMapper(DepartmentMapper.class);
    }

    @Bean
    public WorkerMapper getWorkerMapper() {
        return Mappers.getMapper(WorkerMapper.class);
    }

    @Bean
    public PaymentOrderMapper getPaymentOrderMapper() {
        return Mappers.getMapper(PaymentOrderMapper.class);
    }
}
