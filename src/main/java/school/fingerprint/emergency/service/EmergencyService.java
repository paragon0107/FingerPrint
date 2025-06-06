package school.fingerprint.emergency.service;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.fingerprint.emergency.dto.EmergencyConfirmRequest;
import school.fingerprint.emergency.dto.EmergencyCreateRequest;
import school.fingerprint.emergency.dto.EmergencyInfoResponse;
import school.fingerprint.emergency.dto.EmergencyMemoRequest;
import school.fingerprint.emergency.repository.entity.Emergency;
import school.fingerprint.emergency.repository.EmergencyJpaRepository;
import school.fingerprint.patient.repository.PatientJpaRepository;
import school.fingerprint.patient.repository.entity.Patient;
import school.fingerprint.patient.websocket.PatientWebSocketHandler;

@Service
@RequiredArgsConstructor
public class EmergencyService {

    private final EmergencyJpaRepository emergencyJpaRepository;
    private final PatientJpaRepository patientJpaRepository;
    private final PatientWebSocketHandler handler;

    @Transactional
    public void createEmergency(final EmergencyCreateRequest request) {
        Optional<Patient> patientNullable = patientJpaRepository.findBySsid(request.ssid());
        Patient patient = checkPresent(patientNullable);
        Emergency emergency = Emergency.of(patient.getId());
        emergencyJpaRepository.save(emergency);
        handler.createEmergency(emergency);
        if(handler.isContainPatient(patient.getSsid())){
            handler.updatePatientStatusInfo(
                patient.getSsid(),
                "emergency"
            );
        } else {
            throw new IllegalArgumentException("해당 SSID를 가진 환자가 존재하지 않습니다.");
        }
    }

    @Transactional
    public void confirmEmergency(final EmergencyConfirmRequest request) {
        Emergency emergency = emergencyJpaRepository.findById(request.emergencyId())
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
        emergency.confirm(request.responsibility());
        emergencyJpaRepository.save(emergency);
    }

    @Transactional(readOnly = true)
    public List<EmergencyInfoResponse> getEmergency(final LocalDateTime date) {
        List<EmergencyInfoResponse> responses = new ArrayList<>();
        List<Emergency> emergencies = emergencyJpaRepository.findTop3ByCreatedAtBeforeOrderByCreatedAtDesc(date);
        emergencies.forEach(emergency -> {
            Optional<Patient> patientNullable = patientJpaRepository.findById(emergency.getPatientId());
            Patient patient = checkPresent(patientNullable);
            responses.add(EmergencyInfoResponse.of(emergency, patient));
        });
        return responses;
    }

    private static Patient checkPresent(final Optional<Patient> patient) {
        if(patient.isEmpty()) {
            throw new IllegalArgumentException("해당 SSID를 가진 환자가 존재하지 않습니다.");
        }
        return patient.get();
    }

    public List<EmergencyInfoResponse> getEmergencyByPatientId(final Long patientId) {
        List<EmergencyInfoResponse> responses = new ArrayList<>();
        List<Emergency> emergencies = emergencyJpaRepository.findAllByPatientId(patientId);
        emergencies.forEach(emergency -> {
            Optional<Patient> patientNullable = patientJpaRepository.findById(emergency.getPatientId());
            Patient patient = checkPresent(patientNullable);
            responses.add(EmergencyInfoResponse.of(emergency, patient));
        });
        return responses;
    }

    public EmergencyInfoResponse getEmergencyById(final Long emergencyId) {
        Emergency emergency = emergencyJpaRepository.findById(emergencyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 응급 콜이 존재하지 않습니다."));
        Optional<Patient> patientNullable = patientJpaRepository.findById(emergency.getPatientId());
        Patient patient = checkPresent(patientNullable);
        return EmergencyInfoResponse.of(emergency, patient);
    }

    @Transactional
    public void memoEmergency(EmergencyMemoRequest request) {
        Emergency emergency = emergencyJpaRepository.findById(request.emergencyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 응급 콜이 존재하지 않습니다."));
        emergency.memo(request.memo());
        emergencyJpaRepository.save(emergency);
    }
}
