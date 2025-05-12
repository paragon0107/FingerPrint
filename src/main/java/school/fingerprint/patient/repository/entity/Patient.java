package school.fingerprint.patient.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
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
