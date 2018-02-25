package pl.funnyqrz.services.eventlog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.EventLogEntity;
import pl.funnyqrz.repositories.EventLogRepository;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.eventlog.EventLogService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

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
    public Collection<EventLogEntity> findLast100rows() {
        Collection<EventLogEntity> logs = eventLogRepository.findAll();
        return logs.stream().skip(Math.max(0, logs.size() - LAST_ROWS)).collect(Collectors.toList());
    }

    @Override
    public void registerEvent(String description, LocalDateTime time) {
        EventLogEntity eventLogEntity = new EventLogEntity(description,time);
        eventLogRepository.save(eventLogEntity);
    }

}
