package school.fingerprint.nursecall.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.fingerprint.nursecall.repository.entity.NurseCall;

@Repository
public interface NurseCallJpaRepository extends JpaRepository<NurseCall, Long> {
    List<NurseCall> findTop3ByCreatedAtAfterOrderByCreatedAtDesc(final LocalDateTime createdAt);

    List<NurseCall> findAllByPatientId(final Long patientId);
}
