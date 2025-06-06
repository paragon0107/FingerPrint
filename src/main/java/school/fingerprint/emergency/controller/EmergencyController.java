package school.fingerprint.emergency.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.fingerprint.emergency.dto.EmergencyConfirmRequest;
import school.fingerprint.emergency.dto.EmergencyCreateRequest;
import school.fingerprint.emergency.dto.EmergencyInfoResponse;
import school.fingerprint.emergency.dto.EmergencyMemoRequest;
import school.fingerprint.emergency.service.EmergencyService;
import school.fingerprint.global.dto.SuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emergency")
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

    @GetMapping
    public ResponseEntity<SuccessResponse<List<EmergencyInfoResponse>>> getEmergencyByDate(
            @RequestParam(value = "date", required = true) LocalDateTime date
    ) {
        List<EmergencyInfoResponse> response = emergencyService.getEmergency(date);
        return SuccessResponse.of(
                HttpStatus.OK,
                "응급 콜 조회 성공",
                response
        );
    }

    @PatchMapping
    public ResponseEntity<SuccessResponse<?>> confirmEmergency(
            @RequestBody EmergencyConfirmRequest request
    ) {
        emergencyService.confirmEmergency(request);
        return SuccessResponse.of(
                HttpStatus.CREATED,
                "응급 콜 확인 성공"
        );
    }

    @PutMapping
    public ResponseEntity<SuccessResponse<?>> memoEmergency(
            @RequestBody EmergencyMemoRequest request
    ) {
        emergencyService.memoEmergency(request);
        return SuccessResponse.of(
                HttpStatus.CREATED,
                "응급 콜 메모 성공"
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<SuccessResponse<List<EmergencyInfoResponse>>> getEmergencyByPatientId(
            @PathVariable("patientId") Long patientId
    ) {
        List<EmergencyInfoResponse> response = emergencyService.getEmergencyByPatientId(patientId);
        return SuccessResponse.of(
                HttpStatus.OK,
                "응급 콜 환자 별 조회 성공",
                response
        );
    }

    @GetMapping("/{emergencyId}")
    public ResponseEntity<SuccessResponse<EmergencyInfoResponse>> getEmergencyById(
            @PathVariable("emergencyId") Long emergencyId
    ) {
        EmergencyInfoResponse response = emergencyService.getEmergencyById(emergencyId);
        return SuccessResponse.of(
                HttpStatus.OK,
                "응급 콜 ID 조회 성공",
                response
        );
    }
}
