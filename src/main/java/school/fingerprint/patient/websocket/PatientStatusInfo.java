package school.fingerprint.patient.websocket;

import lombok.Getter;
import school.fingerprint.patient.entity.PatientLocatedInfo;

@Getter
public class PatientStatusInfo extends PatientLocatedInfo {

    private String type;

    public PatientStatusInfo(PatientLocatedInfo patient) {
        super(
                patient.getPatientId(),
                patient.getName(),
                patient.getPlace(),
                patient.getFloor(),
                patient.getX(),
                patient.getY(),
                patient.getSsid()
        );
        this.type = "active";
    }

    public void updateStatus(final String type) {
        this.type = type;
    }

    public void updateLocation(final String place, final int x, final int y) {
        super.place = place;
        super.x = x;
        super.y = y;
    }
}
