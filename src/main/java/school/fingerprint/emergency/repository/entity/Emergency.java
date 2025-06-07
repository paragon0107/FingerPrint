package school.fingerprint.emergency.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.fingerprint.global.entity.BaseTime;
import school.fingerprint.patient.entity.PatientLocatedInfo;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Emergency extends BaseTime {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String Responsibility;

    private String reason;

    private PatientLocatedInfo patientLocatedInfo;

    public static Emergency of(final PatientLocatedInfo patientLocatedInfo) {
        return new Emergency(null,null,null,patientLocatedInfo);
    }

    public void memo(final String reason) {
        this.reason = reason;
    }

    public void confirm(final String Responsibility) {
        this.Responsibility = Responsibility;
    }
}
