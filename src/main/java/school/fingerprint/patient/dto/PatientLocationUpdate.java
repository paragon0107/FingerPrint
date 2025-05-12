package school.fingerprint.patient.dto;

import java.util.Map;

public record PatientLocationUpdate(
        Map<String,String> locations
) {

}
