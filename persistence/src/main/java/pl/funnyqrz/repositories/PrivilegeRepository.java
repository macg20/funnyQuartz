package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.ExchangeRateEntity;
import pl.funnyqrz.entities.account.Privilege;

import java.math.BigInteger;

public interface PrivilegeRepository extends JpaRepository<Privilege,Integer> {
}
