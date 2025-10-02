package com.abc.services.currencyexchange.business.impl;

import com.abc.services.currencyexchange.business.CurrencyExchangeService;
import com.abc.services.currencyexchange.dao.ExchangeQueryLogRepository;
import com.abc.services.currencyexchange.dto.response.CurrencyExchangeResponse;
import com.abc.services.currencyexchange.dto.response.ExternalTodayResponse;
import com.abc.services.currencyexchange.dto.response.RateLimited;
import com.abc.services.currencyexchange.model.ExchangeQueryLog;
import com.abc.services.currencyexchange.proxy.ExternalExchangeClient;
import io.quarkus.runtime.configuration.ConfigurationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.time.*;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private static final Logger Log = Logger.getLogger(CurrencyExchangeServiceImpl.class);
    private static final int DAILY_LIMIT = 10;
    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    @Inject
    ExchangeQueryLogRepository repo;

    @Inject
    @RestClient
    ExternalExchangeClient client;

    @Inject io.smallrye.config.SmallRyeConfig config;

    private ZoneId appZone() {
        var tz = config.getOptionalValue("app.timezone", String.class).orElse("America/Lima");
        try {
            return ZoneId.of(tz);
        } catch (Exception e) {
            throw new ConfigurationException("Invalid app.timezone: " + tz);
        }
    }

    @Override
    @Transactional
    public RateLimited<CurrencyExchangeResponse> getCurrencyExchange(String dni) {
        ZoneId zone = appZone();
        LocalDate today = LocalDate.now(zone);

        long used = repo.countByDniAndDate(dni, today);
        if (used >= DAILY_LIMIT) {
            throw new RateLimitExceededException(dni, (int) used, DAILY_LIMIT, resetIso(zone));
        }

        ExternalTodayResponse ext;
        try {
            ext = client.getToday();
        } catch (Exception e) {
            Log.error("External exchange API error", e);
            throw new UpstreamUnavailableException();
        }

        double buy = pickFirstNonNull(ext.buy, ext.compra);
        double sell = pickFirstNonNull(ext.sell, ext.venta);
        if (Double.isNaN(buy) || Double.isNaN(sell)) {
            Log.error("Unexpected external payload: missing buy/sell rates");
            throw new UpstreamUnavailableException();
        }

        String date = (ext.date != null && !ext.date.isBlank()) ? ext.date : today.format(ISO_DATE);

        ExchangeQueryLog log = new ExchangeQueryLog();
        log.dni = dni;
        log.queryDate = today;
        log.queryTime = LocalDateTime.now(zone);
        log.source = "SUNAT/SBS";
        repo.persist(log);
        int usedPost = (int) used + 1;

        CurrencyExchangeResponse resp = new CurrencyExchangeResponse();
        resp.date = date;
        resp.currency = "USD";
        resp.buyRate = buy;
        resp.sellRate = sell;
        resp.source = "SUNAT/SBS";

        return new RateLimited<>(resp, DAILY_LIMIT, usedPost, resetIso(zone));
    }

    private static String resetIso(ZoneId zone) {
        LocalDate tomorrow = LocalDate.now(zone).plusDays(1);
        ZonedDateTime zdt = ZonedDateTime.of(tomorrow, LocalTime.MIDNIGHT, zone);
        return zdt.withZoneSameInstant(ZoneOffset.UTC).toInstant().toString();
    }

    private static double pickFirstNonNull(Double a, Double b) {
        Double v = a != null ? a : b;
        return v != null ? v : Double.NaN;
    }

    public static class RateLimitExceededException extends RuntimeException {
        public final String dni; public final int used; public final int limit; public final String resetIso;
        public RateLimitExceededException(String dni, int used, int limit, String resetIso) {
            super("Rate limit exceeded");
            this.dni = dni; this.used = used; this.limit = limit; this.resetIso = resetIso;
        }
    }

    public static class UpstreamUnavailableException extends RuntimeException {
        public UpstreamUnavailableException() { super("External exchange rate service unavailable"); }
    }
}