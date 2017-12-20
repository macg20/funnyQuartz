package pl.funnyqrz.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.funnyqrz.entities.ExchangeRateEntity;

import java.math.BigInteger;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity,BigInteger> {

    @Query(value = "SELECT e FROM ExchangeRateEntity e order by e.id desc")
    Page<ExchangeRateEntity> findTop1ByIdOrOrderByIdDesc(Pageable pageable);
}
