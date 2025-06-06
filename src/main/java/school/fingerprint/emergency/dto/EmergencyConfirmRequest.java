package school.fingerprint.emergency.dto;

public record EmergencyConfirmRequest(
        String ssid,
        long emergencyId,
        String responsibility
) {
}
