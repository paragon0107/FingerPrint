package school.fingerprint.emergency.entity;

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
public class Emergency extends BaseTime {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private Long patientId;

    private String reason;

    public static Emergency of(final Long patientId) {
        return new Emergency(null, patientId,null);
    }

    public void confirm(final String reason) {
        this.reason = reason;
    }
}
