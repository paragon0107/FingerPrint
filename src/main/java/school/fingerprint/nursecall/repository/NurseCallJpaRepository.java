package school.fingerprint.nursecall.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import school.fingerprint.nursecall.entity.NurseCall;

public interface NurseCallJpaRepository extends JpaRepository<NurseCall, Long> {
    List<NurseCall> findTop3ByDateAfterOrderByDateDesc(final LocalDate date);

    List<NurseCall> findAllByPatientId(final Long patientId);
}
