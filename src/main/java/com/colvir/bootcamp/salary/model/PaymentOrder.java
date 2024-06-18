/*
Сущность "Платежное поручение": Платежное поручение привязано к одному Работнику (Worker)
* */
package com.colvir.bootcamp.salary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentOrder {

    @Id
    @SequenceGenerator(name = "payments_seq", sequenceName = "payments_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payments_seq")
    private Integer id;
    private Date date;
    private float sum;
    @ManyToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private Worker worker;

}
