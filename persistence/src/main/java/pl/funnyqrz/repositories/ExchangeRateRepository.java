package pl.funnyqrz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.ExchangeRateEntity;

import java.math.BigInteger;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity,BigInteger> {
}
