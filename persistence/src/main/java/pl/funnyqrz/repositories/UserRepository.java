package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.funnyqrz.entities.account.Privilege;
import pl.funnyqrz.entities.account.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,BigInteger> {

    Optional<User> findByEmail(String email);

    @Query("SELECT 1 FROM User u WHERE u.email = :email")
    Collection<Integer> ifExistsUserEmail(@Param("email") String email);
}
