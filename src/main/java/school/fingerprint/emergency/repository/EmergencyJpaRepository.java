package school.fingerprint.emergency.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.fingerprint.emergency.repository.entity.Emergency;

@Repository
public interface EmergencyJpaRepository extends JpaRepository<Emergency, Long> {
    List<Emergency> findTop3ByCreatedAtBeforeOrderByCreatedAtDesc(final LocalDateTime createdAt);

    List<Emergency> findAllByPatientLocatedInfoPatientId(final Long patientId);
}
