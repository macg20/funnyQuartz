package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.account.Privilege;
import pl.funnyqrz.entities.account.Role;

import java.math.BigInteger;

public interface RoleRepository extends JpaRepository<Role,BigInteger> {
}
