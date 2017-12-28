package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.account.Privilege;
import pl.funnyqrz.entities.account.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,BigInteger> {

    Optional<User> findByEmail(String email);
}
