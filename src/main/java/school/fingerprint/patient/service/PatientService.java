package school.fingerprint.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.fingerprint.patient.dto.PatientCreateRequest;
import school.fingerprint.patient.dto.PatientLocationUpdate;
import school.fingerprint.patient.entity.PatientLocatedInfo;
import school.fingerprint.patient.port.AiServerClient;
import school.fingerprint.patient.port.dto.PredictedLocation;
import school.fingerprint.patient.repository.PatientJpaRepository;
import school.fingerprint.patient.repository.entity.Patient;
import school.fingerprint.patient.websocket.PatientWebSocketHandler;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientService {

    private final PatientJpaRepository patientRepository;
    private final PatientWebSocketHandler locationHandler;
    private final AiServerClient aiServerClient;

    @Transactional
    public void createPatient(final PatientCreateRequest request) {
        existPatientDevice(request.ssid());
        Patient patient = Patient.of(
                request.name(),
                request.birth(),
                request.ssid()
        );
        patientRepository.save(patient);
    }

    @Transactional
    public void updatePatientLocation(final String ssid, PatientLocationUpdate request) {
        Patient patient = getPatient(ssid);
        PredictedLocation location = aiServerClient.getPatientStatusFromAi(request);
        PatientLocatedInfo patientLocatedInfo = PatientLocatedInfo.of(
                patient,
                location.predicted_location()
        );
        locationHandler.updatePatientLocatedInfo(patientLocatedInfo);
        System.out.println("환자 이름: " + patient.getName());
        System.out.println("환자 ssid : " + ssid);
        System.out.println("업데이트 시간" + new Date());
        System.out.println("환자 위치 : " + location);
        System.out.println("환자 위치 층: " + patientLocatedInfo.getFloor());
        System.out.println(request.toString());
    }

    public Patient getPatient(final String ssid) {
        return patientRepository.findBySsid(ssid).orElseThrow(
                () -> new IllegalArgumentException("환자 없음")
        );
    }

    private void existPatientDevice(final String ssid) {
        patientRepository.findBySsid(ssid).ifPresent(
                patient -> {
                    throw new IllegalArgumentException("이미 등록된 기기입니다.");
                }
        );
    }

    public void deletePatientLocation(final String ssid) {
        locationHandler.deletePatientInfo(ssid);
    }

    public Patient getPatientById(final long patientId) {
        return patientRepository.findById(patientId).orElseThrow(
                () -> new IllegalArgumentException("환자 없음")
        );
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }
}
