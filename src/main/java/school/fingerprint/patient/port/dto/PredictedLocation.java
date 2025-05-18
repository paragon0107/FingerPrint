package school.fingerprint.patient.port.dto;

public record PredictedLocation(
        String status_code,
        String message,
        String predicted_location
) {
}
