package school.fingerprint.patient.websocket;

import lombok.Getter;
import school.fingerprint.patient.entity.PatientLocatedInfo;

@Getter
public class PatientStatusInfo extends PatientLocatedInfo {

    private final String type;

    public PatientStatusInfo(PatientLocatedInfo patient) {
        super(
                patient.getId(),
                patient.getName(),
                patient.getPlace(),
                patient.getX(),
                patient.getY(),
                patient.getSsid()
        );
        this.type = "active";
    }
}
