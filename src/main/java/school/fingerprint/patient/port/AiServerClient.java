package school.fingerprint.patient.port;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import school.fingerprint.patient.port.dto.PredictedLocation;
import school.fingerprint.patient.port.dto.RssiRequest;

import java.util.*;

@Component
public class AiServerClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PredictedLocation getPatientStatusFromAi(final Map<String, Integer> data) {
        String url = "http://3.34.107.14:8000/predict";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RssiRequest> request = new HttpEntity<>(new RssiRequest(data), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        try {
            // 응답 본문을 PredictedLocation 객체로 변환
            return objectMapper.readValue(response.getBody(), PredictedLocation.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 예외 처리
        }
    }
}
