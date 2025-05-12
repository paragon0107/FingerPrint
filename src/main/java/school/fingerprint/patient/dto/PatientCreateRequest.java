package school.fingerprint.patient.dto;

import lombok.NonNull;

import java.time.LocalDate;

public record PatientCreateRequest(
        @NonNull String name,
        @NonNull LocalDate birth,
        @NonNull String ssid
) {

}
