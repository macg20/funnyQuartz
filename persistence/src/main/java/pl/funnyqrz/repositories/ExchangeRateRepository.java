package pl.funnyqrz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.funnyqrz.entities.ExchangeRateEntity;

import java.math.BigInteger;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, BigInteger> {

    @Query(nativeQuery = true, value = "SELECT * FROM exchange_rates order by id desc limit 1")
    ExchangeRateEntity findLast();

}
