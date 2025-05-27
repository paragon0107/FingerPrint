package school.fingerprint.nursecall.dto;

public record NurseCallConfirmRequest(
        String ssid,
        long nurseCallId,
        String reason
) {
}
