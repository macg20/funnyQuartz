package pl.funnyqrz.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;


@Entity
@GenericGenerator(name = "exchange_rate_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "echange_rate_sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_value", value = "1")})
@Table(name = "exchange_rates")
@Data
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(generator = "exchange_rate_generator")
    private BigInteger id;
    private BigDecimal usdExchangeRate; //dolar amerykanski
    private BigDecimal eurExchangeRate; //euro
    private BigDecimal chfExchangeRate; //frank szwajcarski
    private BigDecimal gbfExchangeRate; // funt szterling
    private LocalDateTime createDate;

    @Version
    @Column(name = "version")
    private long version;
}
