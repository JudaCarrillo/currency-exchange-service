package com.abc.services.currencyexchange.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_query_log", indexes = {
        @Index(name = "idx_dni_date", columnList = "dni, queryDate")
})
public class ExchangeQueryLog extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 8)
    public String dni;

    @Column(nullable = false)
    public LocalDate queryDate;

    @Column(nullable = false)
    public LocalDateTime queryTime;

    @Column(nullable = false, length = 20)
    public String source;
}
