package school.fingerprint.nursecall.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.fingerprint.global.entity.BaseTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class NurseCall extends BaseTime {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private Long patientId;

    private String responsibility;

    private String reason;

    public static NurseCall of(final Long patientId) {
        return new NurseCall(null, patientId,null,null);
    }

    public void confirm(final String responsibility) {
        this.responsibility = responsibility;
    }

    public void memo(final String reason) {
        this.reason = reason;
    }
}
