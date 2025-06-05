package school.fingerprint.nursecall.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.fingerprint.nursecall.dto.NurseCallConfirmRequest;
import school.fingerprint.nursecall.dto.NurseCallCreateRequest;
import school.fingerprint.nursecall.dto.NurseCallInfoResponse;
import school.fingerprint.nursecall.repository.NurseCallJpaRepository;
import school.fingerprint.nursecall.repository.entity.NurseCall;
import school.fingerprint.patient.repository.PatientJpaRepository;
import school.fingerprint.patient.repository.entity.Patient;
import school.fingerprint.patient.websocket.PatientWebSocketHandler;

@Service
@RequiredArgsConstructor
public class NurseCallService {

    private final NurseCallJpaRepository nurseCallJpaRepository;
    private final PatientJpaRepository patientJpaRepository;
    private final PatientWebSocketHandler handler;

    @Transactional
    public void createNurseCall(final NurseCallCreateRequest request) {
        Optional<Patient> patientNullable = patientJpaRepository.findBySsid(request.ssid());
        Patient patient = checkPresent(patientNullable);
        nurseCallJpaRepository.save(NurseCall.of(patient.getId()));

        if(handler.isContainPatient(patient.getSsid())){
            handler.updatePatientStatusInfo(
                patient.getSsid(),
                "help"
            );
        } else {
            throw new IllegalArgumentException("해당 SSID를 가진 환자가 존재하지 않습니다.");
        }
    }

    @Transactional
    public void confirmNurseCall(final NurseCallConfirmRequest request) {
        NurseCall nurseCall = nurseCallJpaRepository.findById(request.nurseCallId())
                .orElseThrow(() -> new IllegalArgumentException("해당 응급 콜이 존재하지 않습니다."));
        Optional<Patient> patientNullable = patientJpaRepository.findBySsid(request.ssid());
        Patient patient = checkPresent(patientNullable);
        if(handler.isContainPatient(patient.getSsid())){
            handler.updatePatientStatusInfo(
                patient.getSsid(),
                "active"
            );
        } else {
            throw new IllegalArgumentException("해당 SSID를 가진 환자가 존재하지 않습니다.");
        }
        nurseCall.confirm(request.reason());
        nurseCallJpaRepository.save(nurseCall);
    }

    @Transactional(readOnly = true)
    public List<NurseCallInfoResponse> getNurseCall(final LocalDateTime date) {
        List<NurseCallInfoResponse> responses = new ArrayList<>();
        List<NurseCall> nurseCalls = nurseCallJpaRepository.findTop3ByCreatedAtBeforeOrderByCreatedAtDesc(
                date);
        nurseCalls.forEach(nurseCall -> {
            Optional<Patient> patientNullable = patientJpaRepository.findById(nurseCall.getPatientId());
            Patient patient = checkPresent(patientNullable);
            responses.add(NurseCallInfoResponse.of(nurseCall, patient));
        });
        return responses;
    }

    private static Patient checkPresent(final Optional<Patient> patient) {
        if(patient.isEmpty()) {
            throw new IllegalArgumentException("해당 SSID를 가진 환자가 존재하지 않습니다.");
        }
        return patient.get();
    }

    public List<NurseCallInfoResponse> getNurseCallByPatientId(final Long patientId) {
        List<NurseCallInfoResponse> responses = new ArrayList<>();
        List<NurseCall> nurseCalls = nurseCallJpaRepository.findAllByPatientId(patientId);
        nurseCalls.forEach(nurseCall -> {
            Optional<Patient> patientNullable = patientJpaRepository.findById(nurseCall.getPatientId());
            Patient patient = checkPresent(patientNullable);
            responses.add(NurseCallInfoResponse.of(nurseCall, patient));
        });
        return responses;
    }

    public NurseCallInfoResponse getNurseCallById(final Long emergencyId) {
        NurseCall nurseCall = nurseCallJpaRepository.findById(emergencyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 응급 콜이 존재하지 않습니다."));
        Optional<Patient> patientNullable = patientJpaRepository.findById(nurseCall.getPatientId());
        Patient patient = checkPresent(patientNullable);
        return NurseCallInfoResponse.of(nurseCall, patient);
    }
}
