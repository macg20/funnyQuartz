package pl.funnyqrz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.funnyqrz.entities.ExchangeRateEntity;

import java.math.BigInteger;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity,BigInteger> {

    @Query("SELECT x from ExchangeRateEntity x order by x.id desc")
    ExchangeRateEntity findLast();
}
