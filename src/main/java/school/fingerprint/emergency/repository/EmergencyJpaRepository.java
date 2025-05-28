package school.fingerprint.emergency.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import school.fingerprint.emergency.repository.entity.Emergency;

public interface EmergencyJpaRepository extends JpaRepository<Emergency, Long> {
    List<Emergency> findTop3ByDateAfterOrderByDateDesc(final LocalDate date);

    List<Emergency> findAllByPatientId(final Long patientId);
}
