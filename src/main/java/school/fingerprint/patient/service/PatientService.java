package school.fingerprint.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.fingerprint.patient.dto.PatientCreateRequest;
import school.fingerprint.patient.dto.PatientLocationUpdate;
import school.fingerprint.patient.entity.PatientLocatedInfo;
import school.fingerprint.patient.repository.entity.Patient;
import school.fingerprint.patient.port.AiServerClient;
import school.fingerprint.patient.repository.PatientJpaRepository;
import school.fingerprint.patient.websocket.PatientWebSocketHandler;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientService {

    private final PatientJpaRepository patientRepository;
    private final PatientWebSocketHandler locationHandler;
    private final AiServerClient aiServerClient;

    public void createPatient(final PatientCreateRequest request) {
        Patient patient = Patient.of(
                request.name(),
                request.birth(),
                request.ssid()
        );
        patientRepository.save(patient);
    }

    public void updatePatientLocation(final String ssid, PatientLocationUpdate request) {
        Patient patient = getPatient(ssid);
        String location = aiServerClient.getPatientStatusFromAi(request.locations());
        locationHandler.updatePatientInfo(PatientLocatedInfo.of(
                patient,
                location
        ));
    }

    private Patient getPatient(final String ssid) {
        return patientRepository.findBySsid(ssid).orElseThrow(
                () -> new IllegalArgumentException("환자 없음")
        );
    }
}
