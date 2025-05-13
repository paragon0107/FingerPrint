package school.fingerprint.patient.port.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record RssiRequest(
        @JsonProperty("mac_rssi") Map<String, Integer> macRssi
) {
    @JsonCreator
    public RssiRequest(@JsonProperty("mac_rssi") Map<String, Integer> macRssi) {
        this.macRssi = macRssi;
    }
}
