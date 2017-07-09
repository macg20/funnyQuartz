package pl.funnyqrz.services.exchangerate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.funnyqrz.entities.EventLogEntity;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.repositories.ExchangeRateRepository;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.eventlog.EventLogService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ExchangeRateServiceImpl extends AbstractService implements ExchangeRateService {

    private EventLogService eventLogService;
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateServiceImpl(EventLogService eventLogService, ExchangeRateRepository exchangeRateRepository) {
        this.eventLogService = eventLogService;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public void save(ExchangeRateEntity exchangeRateEntity) {
        EventLogEntity eventLogEntity = new EventLogEntity("Save exchange rate", LocalDateTime.now());
        eventLogService.save(eventLogEntity);
        exchangeRateRepository.save(exchangeRateEntity);
    }
}
