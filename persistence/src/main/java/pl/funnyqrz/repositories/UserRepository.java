package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.funnyqrz.entities.account.UserEntity;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, BigInteger> {
    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email")String email);

    @Query("SELECT 1 FROM UserEntity u WHERE u.email = :email")
    Collection<Integer> ifExistsUserEmail(@Param("email") String email);

    @Query("SELECT u.email FROM UserEntity u where u.enabled = true")
    Set<String> findAllEmails();


}
