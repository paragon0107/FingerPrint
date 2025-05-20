package school.fingerprint.patient.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import school.fingerprint.patient.entity.PatientLocatedInfo;

import jakarta.websocket.Session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PatientWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final Map<String, PatientLocatedInfo> patientMap = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        String json = mapper.writeValueAsString(patientMap.values());
        session.sendMessage(new TextMessage(json));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        sessions.remove(session);
        if (session.isOpen()) {
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    public void updatePatientInfo(final PatientLocatedInfo info) {
        PatientStatusInfo patientStatusInfo = new PatientStatusInfo(info);
        patientMap.put(patientStatusInfo.getSsid(), patientStatusInfo);
        String json;
        try {
            json = mapper.writeValueAsString(patientStatusInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        broadcast(json);
    }

    public void deletePatientInfo(final String ssid) {
        patientMap.remove(ssid);
        String json;
        try {
            json = mapper.writeValueAsString(Map.of("type", "delete", "ssid", ssid));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        broadcast(json);
    }

    private void broadcast(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        session.close(CloseStatus.SERVER_ERROR);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    sessions.remove(session);
                }
            } else {
                sessions.remove(session);
            }
        }
    }

    // --- 10초마다 ping 보내기 (Heartbeat) ---
    @Scheduled(fixedRate = 10000)
    public void sendPing() {
        ByteBuffer payload = ByteBuffer.wrap(new byte[]{1});
        for (WebSocketSession session : sessions) {
            if (session.isOpen() && session instanceof StandardWebSocketSession) {
                try {
                    StandardWebSocketSession stdSession = (StandardWebSocketSession) session;
                    Session nativeSession = stdSession.getNativeSession(Session.class);
                    if (nativeSession.isOpen()) {
                        nativeSession.getAsyncRemote().sendPing(payload);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        session.close(CloseStatus.SERVER_ERROR);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    sessions.remove(session);
                }
            } else {
                sessions.remove(session);
            }
        }
    }
}
