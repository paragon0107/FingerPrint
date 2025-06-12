package school.fingerprint.patient.dto;

import java.util.Map;

public record PatientLocationUpdate(
        Map<String,Integer> location1,
        Map<String,Integer> location2,
        Map<String,Integer> location3
) {

}
