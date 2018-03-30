package pl.funnyqrz.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pl.funnyqrz.enums.EventLogType;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@GenericGenerator(name = "event_log_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "event_log_sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_value", value = "1")})
@Table(name = "event_logs")
@Data
public class EventLogEntity {

    @Id
    @GeneratedValue(generator = "event_log_generator")
    private BigInteger id;
    private String description;
    private LocalDateTime date;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    EventLogType type;

    public EventLogEntity() {

    }

    public EventLogEntity(String description, LocalDateTime date) {
        this.description = description;
        this.date = date;
    }

    public EventLogEntity(BigInteger id, String description, LocalDateTime date) {
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public EventLogEntity(String description, LocalDateTime date, EventLogType type) {
        this.description = description;
        this.date = date;
        this.type = type;
    }
}