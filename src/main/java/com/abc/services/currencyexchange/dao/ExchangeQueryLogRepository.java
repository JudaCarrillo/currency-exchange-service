package com.abc.services.currencyexchange.dao;

import com.abc.services.currencyexchange.model.ExchangeQueryLog;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;

@ApplicationScoped
public class ExchangeQueryLogRepository implements PanacheRepository<ExchangeQueryLog> {

    public long countByDniAndDate(String dni, LocalDate date) {
        return count("dni = ?1 and queryDate = ?2", dni, date);
    }
}
