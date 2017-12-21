package pl.funnyqrz.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@GenericGenerator(name = "exchange_rate_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "echange_rate_sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_value", value = "1")})
@Table(name = "exchange_rates")
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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigDecimal getUsdExchangeRate() {
        return usdExchangeRate;
    }

    public void setUsdExchangeRate(BigDecimal usdExchangeRate) {
        this.usdExchangeRate = usdExchangeRate;
    }

    public BigDecimal getEurExchangeRate() {
        return eurExchangeRate;
    }

    public void setEurExchangeRate(BigDecimal eurExchangeRate) {
        this.eurExchangeRate = eurExchangeRate;
    }

    public BigDecimal getChfExchangeRate() {
        return chfExchangeRate;
    }

    public void setChfExchangeRate(BigDecimal chfExchangeRate) {
        this.chfExchangeRate = chfExchangeRate;
    }

    public BigDecimal getGbfExchangeRate() {
        return gbfExchangeRate;
    }

    public void setGbfExchangeRate(BigDecimal gbfExchangeRate) {
        this.gbfExchangeRate = gbfExchangeRate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
