package school.fingerprint.nursecall.dto;

import school.fingerprint.nursecall.repository.entity.NurseCall;
import school.fingerprint.patient.entity.PatientLocatedInfo;
import school.fingerprint.patient.repository.entity.Patient;

public record NurseCallInfoResponse(
        long id,
        long patientId,
        String patientName,
        String reason,
        String createdAt,
        String updatedAt,
        PatientLocatedInfo patientLocatedInfo
) {
    public static NurseCallInfoResponse of(
            final NurseCall nurseCall,
            final Patient patient
            ) {
        return new NurseCallInfoResponse(
                nurseCall.getId(),
                nurseCall.getPatientId(),
                patient.getName(),
                nurseCall.getReason(),
                nurseCall.getCreatedAt().toString(),
                nurseCall.getUpdatedAt().toString(),
                nurseCall.getPatientLocatedInfo()
        );
    }
}
