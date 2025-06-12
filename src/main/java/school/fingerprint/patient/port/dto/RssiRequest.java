package school.fingerprint.patient.port.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import school.fingerprint.patient.dto.PatientLocationUpdate;

import java.util.Map;

public record RssiRequest(
        @JsonProperty("mac_rssi") PatientLocationUpdate macRssi
) {
    @JsonCreator
    public RssiRequest(@JsonProperty("mac_rssi") PatientLocationUpdate macRssi) {
        this.macRssi = macRssi;
    }
}
