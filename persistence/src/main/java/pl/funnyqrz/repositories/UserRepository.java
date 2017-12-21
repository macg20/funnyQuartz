package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.account.Privilege;
import pl.funnyqrz.entities.account.User;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface UserRepository extends JpaRepository<User,BigInteger> {
}
