package pl.funnyqrz.tests.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import pl.funnyqrz.entities.EventLogEntity;
import pl.funnyqrz.enums.EventLogType;
import pl.funnyqrz.repositories.EventLogRepository;
import pl.funnyqrz.services.eventlog.EventLogService;
import pl.funnyqrz.tests.AbstractTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Transactional
@Rollback
public class EventLogServiceTest extends AbstractTest {

    @Autowired
    EventLogRepository eventLogRepository;

    @Autowired
    EventLogService eventLogService;

    @BeforeEach
    void setUp() {
        EventLogEntity testObject1 = new EventLogEntity();
        testObject1.setType(EventLogType.ERROR);
        testObject1.setDescription("test1");
        testObject1.setDate(LocalDateTime.now());

        EventLogEntity testObject2 = new EventLogEntity();
        testObject2.setType(EventLogType.ERROR);
        testObject2.setDescription("test2");
        testObject2.setDate(LocalDateTime.now());

        eventLogRepository.save(testObject1);
        eventLogRepository.save(testObject2);
    }

    @AfterEach
    public void tearDown() {
        eventLogRepository.deleteAll();
    }

    @Test
    public void readRepositoryTest() {

        //when
        Collection<EventLogEntity> logs = eventLogService.findLast100Events();
        //then
        assertThat(logs).isNotEmpty();
        Set<String> descritpion = logs.stream()
                .map(e -> e.getDescription())
                .collect(Collectors.toSet());
        assertThat(descritpion.contains("test1")).isTrue();
        assertThat(descritpion.contains("test2")).isTrue();

    }

}


