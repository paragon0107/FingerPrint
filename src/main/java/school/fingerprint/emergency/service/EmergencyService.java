package school.fingerprint.emergency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.fingerprint.emergency.dto.EmergencyCreateRequest;
import school.fingerprint.emergency.entity.Emergency;
import school.fingerprint.emergency.repository.EmergencyJpaRepository;
import school.fingerprint.patient.repository.PatientJpaRepository;
import school.fingerprint.patient.repository.entity.Patient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmergencyService {

    private final EmergencyJpaRepository emergencyJpaRepository;
    private final PatientJpaRepository patientJpaRepository;

    public void createEmergency(final EmergencyCreateRequest request) {
        Optional<Patient> patientNullable = patientJpaRepository.findBySsid(request.ssid());
        Patient patient = checkPresent(patientNullable);
        emergencyJpaRepository.save(Emergency.of(patient.getId()));
        //웹소켓 데이터 불러와서 그 환자가 소켓 상에 있는지 확인.
        //소켓상에 있는 데이터들을 emergency로 변경

    }

    private static Patient checkPresent(final Optional<Patient> patient) {
        if(patient.isEmpty()) {
            throw new IllegalArgumentException("해당 SSID를 가진 환자가 존재하지 않습니다.");
        }
        return patient.get();
    }
}
