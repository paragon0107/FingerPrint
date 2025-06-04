package school.fingerprint.nursecall.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.fingerprint.global.dto.SuccessResponse;
import school.fingerprint.nursecall.dto.NurseCallConfirmRequest;
import school.fingerprint.nursecall.dto.NurseCallCreateRequest;
import school.fingerprint.nursecall.dto.NurseCallInfoResponse;
import school.fingerprint.nursecall.service.NurseCallService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nurse-call")
public class NurseCallController {

    private final NurseCallService nurseCallService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createNurseCall(
            @RequestBody NurseCallCreateRequest request
    ) {
        nurseCallService.createNurseCall(request);
        return SuccessResponse.of(
                HttpStatus.CREATED,
                "널스 콜 전달 성공"
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<NurseCallInfoResponse>>> getNurseCallByDate(
            @RequestParam(value = "date", required = true) LocalDateTime date
    ) {
        List<NurseCallInfoResponse> response = nurseCallService.getNurseCall(date);
        return SuccessResponse.of(
                HttpStatus.OK,
                "널스 콜 조회 성공",
                response
        );
    }

    @PatchMapping
    public ResponseEntity<SuccessResponse<?>> confirmNurseCall(
            @RequestBody NurseCallConfirmRequest request
    ) {
        nurseCallService.confirmNurseCall(request);
        return SuccessResponse.of(
                HttpStatus.CREATED,
                "널스 콜 확인 성공"
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<SuccessResponse<List<NurseCallInfoResponse>>> getNurseCallByPatientId(
            @PathVariable("patientId") Long patientId
    ) {
        List<NurseCallInfoResponse> response = nurseCallService.getNurseCallByPatientId(patientId);
        return SuccessResponse.of(
                HttpStatus.OK,
                "널스 콜 환자 별 조회 성공",
                response
        );
    }

    @GetMapping("/{nurseCallId}")
    public ResponseEntity<SuccessResponse<NurseCallInfoResponse>> getNurseCallById(
            @PathVariable("nurseCallId") Long nurseCallId
    ) {
        NurseCallInfoResponse response = nurseCallService.getNurseCallById(nurseCallId);
        return SuccessResponse.of(
                HttpStatus.OK,
                "널스 콜 ID 조회 성공",
                response
        );
    }
}
