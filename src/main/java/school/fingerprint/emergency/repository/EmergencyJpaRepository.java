package school.fingerprint.emergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.fingerprint.emergency.entity.Emergency;

public interface EmergencyJpaRepository extends JpaRepository<Emergency, Long> {
    // Define any custom query methods if needed
}
