package school.fingerprint.emergency.dto;

public record EmergencyMemoRequest(
        long emergencyId,
        String memo
) {
}
