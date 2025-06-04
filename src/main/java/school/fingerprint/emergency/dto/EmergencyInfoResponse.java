package school.fingerprint.emergency.dto;

import school.fingerprint.emergency.repository.entity.Emergency;
import school.fingerprint.patient.repository.entity.Patient;

public record EmergencyInfoResponse(
        long id,
        long patientId,
        String patientName,
        String reason,
        String createdAt,
        String updatedAt
) {
    public static EmergencyInfoResponse of(
            final Emergency emergency,
            final Patient patient
            ) {
        return new EmergencyInfoResponse(
                emergency.getId(),
                emergency.getPatientId(),
                patient.getName(),
                emergency.getReason(),
                emergency.getCreatedAt().toString(),
                emergency.getUpdatedAt().toString()
        );
    }
}
