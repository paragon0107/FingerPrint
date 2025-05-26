package school.fingerprint.patient.entity;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import school.fingerprint.patient.repository.entity.Patient;

@AllArgsConstructor
@Getter
public class PatientLocatedInfo {
    long id;
    String name;
    protected String place;
    protected int x;
    protected int y;
    String ssid;

    public static PatientLocatedInfo of(
            final Patient patient,
            final String locationInfo
    ) {
        String[] location = locationInfo.split("_");
        String place = String.join("_", Arrays.copyOf(location, location.length - 2));
        return new PatientLocatedInfo(
                patient.getId(),
                patient.getName(),
                place,
                Integer.parseInt(location[location.length - 2]),
                Integer.parseInt(location[location.length - 1]),
                patient.getSsid()
        );
    }
}
