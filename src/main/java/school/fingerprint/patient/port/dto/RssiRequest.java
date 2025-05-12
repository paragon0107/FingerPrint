package school.fingerprint.patient.port.dto;

import java.util.Map;

public record RssiRequest(
        Map<String, String> mac_rssi
) {
}
