package pl.funnyqrz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.funnyqrz.entities.ReportEntity;

public interface ReportRepository extends JpaRepository<ReportEntity,Long> {
}
