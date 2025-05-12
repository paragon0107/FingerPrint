package school.fingerprint.patient.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class Patient {
    @Id
    private Long id;
    private String name;
    private LocalDate birth;
    private String ssid;

    public static Patient of(final String name, final LocalDate birth,final String ssid) {
        Patient patient = new Patient();
        patient.name = name;
        patient.birth = birth;
        patient.ssid = ssid;
        return patient;
    }
}
