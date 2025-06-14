package school.fingerprint.patient.entity;

import jakarta.persistence.Embeddable;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.fingerprint.patient.repository.entity.Patient;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class PatientLocatedInfo {
    long patientId;
    String name;
    protected String place;
    protected int floor;
    protected int x;
    protected int y;
    String ssid;

    public static PatientLocatedInfo of(
            final Patient patient,
            final String locationInfo
    ) {
        int floor;
        String[] location = locationInfo.split("_");
        if (location[1].startsWith("6")) {
            floor = 6;
        } else {
            if ((location[1].equals("5r") || location[1].equals("5l")) && location[2].equals("4")) {
                floor = 6;
            } else {
                floor = 5;
            }
        }
        String place = String.join("_", Arrays.copyOf(location, location.length - 2));
        return new PatientLocatedInfo(
                patient.getId(),
                patient.getName(),
                place,
                floor,
                Integer.parseInt(location[location.length - 2]),
                Integer.parseInt(location[location.length - 1]),
                patient.getSsid()
        );
    }
}
