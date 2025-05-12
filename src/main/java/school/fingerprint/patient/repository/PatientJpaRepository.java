package school.fingerprint.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.fingerprint.patient.repository.entity.Patient;

import java.util.Optional;

@Repository
public interface PatientJpaRepository  extends JpaRepository<Patient, Long> {
    Optional<Patient> findBySsid(final String ssid);
}
