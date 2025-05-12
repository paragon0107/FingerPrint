package school.fingerprint.patient.port;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import school.fingerprint.patient.port.dto.RssiRequest;

import java.util.*;

@Component
public class AiServerClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getPatientStatusFromAi(final Map<String, String> rssiList) {
        String url = "http://3.34.107.14:8000/predict";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RssiRequest> request = new HttpEntity<>(new RssiRequest(rssiList), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return response.getBody(); // ex: "ROOM_A_101" 같은 문자열
    }
}
