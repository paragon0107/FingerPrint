package school.fingerprint.emergency.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.fingerprint.emergency.dto.EmergencyCreateRequest;
import school.fingerprint.emergency.service.EmergencyService;
import school.fingerprint.global.dto.SuccessResponse;
import school.fingerprint.patient.dto.PatientCreateRequest;
import school.fingerprint.patient.dto.PatientLocationUpdate;
import school.fingerprint.patient.service.PatientService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class EmergencyController {

    private final EmergencyService emergencyService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createEmergency(
            @RequestBody EmergencyCreateRequest request
    ) {
        emergencyService.createEmergency(request);
        return SuccessResponse.of(
                HttpStatus.CREATED,
                "응급 콜 전달 성공"
        );
    }
}
