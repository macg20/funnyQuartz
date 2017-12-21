package pl.funnyqrz.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@GenericGenerator(name = "event_log_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "event_log_sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_value", value = "1")})
@Table(name = "event_logs")
public class EventLogEntity {

    @Id
    @GeneratedValue(generator ="event_log_generator" )
    private BigInteger id;
    private String description;
    private LocalDateTime date;

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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}