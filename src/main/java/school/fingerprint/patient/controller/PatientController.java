package school.fingerprint.patient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.fingerprint.global.dto.SuccessResponse;
import school.fingerprint.patient.dto.PatientCreateRequest;
import school.fingerprint.patient.dto.PatientLocationUpdate;
import school.fingerprint.patient.service.PatientService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createPatient(
            @RequestBody PatientCreateRequest request
    ) {
        patientService.createPatient(request);
        return SuccessResponse.of(
                HttpStatus.CREATED,
                "환자 등록 성공"
        );
    }

    @PutMapping("/{ssid}")
    public ResponseEntity<SuccessResponse<?>> updatePatientLocation(
            @PathVariable("ssid") String ssid,
            @RequestBody PatientLocationUpdate request
    ) {
        patientService.updatePatientLocation(ssid, request);
        return SuccessResponse.of(
                HttpStatus.OK,
                "환자 위치 업데이트 성공"
        );
    }

    @DeleteMapping("/{ssid}")
    public ResponseEntity<SuccessResponse<?>> deletePatientLocation(
            @PathVariable("ssid") String ssid
    ) {
        patientService.deletePatientLocation(ssid);
        return SuccessResponse.of(
                HttpStatus.OK,
                "환자 위치 업데이트 성공"
        );
    }
}
