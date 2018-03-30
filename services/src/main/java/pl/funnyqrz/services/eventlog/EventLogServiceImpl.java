package pl.funnyqrz.services.eventlog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.EventLogEntity;
import pl.funnyqrz.enums.EventLogType;
import pl.funnyqrz.repositories.EventLogRepository;
import pl.funnyqrz.services.AbstractService;

import java.time.LocalDateTime;
import java.util.Collection;

@Transactional
@Service
public class EventLogServiceImpl extends AbstractService implements EventLogService {

    private final static int LAST_ROWS = 100;

    private EventLogRepository eventLogRepository;

    @Autowired
    public EventLogServiceImpl(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @Override
    public Collection<EventLogEntity> findAll() {
        getLogger().info("fetch event logs");
        return eventLogRepository.findAll();
    }

    @Override
    public Collection<EventLogEntity> findLast100Events() {
        Collection<EventLogEntity> logs = eventLogRepository.findLast100Events();
        return logs;
    }

    @Override
    public void registerEvent(String description, LocalDateTime time, EventLogType type) {
        EventLogEntity eventLogEntity = new EventLogEntity(description, time, type);
        eventLogRepository.save(eventLogEntity);
    }

}
