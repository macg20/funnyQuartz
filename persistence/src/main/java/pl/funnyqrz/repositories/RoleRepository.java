package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.account.RoleEntity;

import java.math.BigInteger;

public interface RoleRepository extends JpaRepository<RoleEntity,BigInteger> {

    RoleEntity findByName(String name);
}
