package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.ReportEntity;

import java.math.BigInteger;

public interface ReportRepository extends JpaRepository<ReportEntity,BigInteger> {
}
