package school.fingerprint.patient.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import school.fingerprint.patient.entity.PatientLocatedInfo;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PatientWebSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final Map<String, PatientLocatedInfo> patientMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        // 연결 직후 전체 환자 리스트 전송
        try {
            String json = new ObjectMapper().writeValueAsString(patientMap.values());
            session.sendMessage(new TextMessage(json));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePatientInfo(final PatientLocatedInfo info) {
        patientMap.put(info.ssid(), info);
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(info);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(json));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
