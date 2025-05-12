package school.fingerprint.patient.entity;

import school.fingerprint.patient.repository.entity.Patient;

import java.util.Arrays;

public record PatientLocatedInfo(
        long id,
        String name,
        String place,
        int x,
        int y,
        String ssid
) {
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
                Integer.parseInt(location[location.length-2]),
                Integer.parseInt(location[location.length-1]),
                patient.getSsid()
        );
    }
}
