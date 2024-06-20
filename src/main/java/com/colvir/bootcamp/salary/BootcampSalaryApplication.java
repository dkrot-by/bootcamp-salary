package com.colvir.bootcamp.salary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableTransactionManagement
public class BootcampSalaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootcampSalaryApplication.class, args);
    }

}
