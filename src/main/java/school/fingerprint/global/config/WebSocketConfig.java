package school.fingerprint.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import school.fingerprint.patient.websocket.PatientWebSocketHandler;

@Configuration
@EnableWebSocket

public class WebSocketConfig implements WebSocketConfigurer {

    private final PatientWebSocketHandler wsHandler;

    public WebSocketConfig(PatientWebSocketHandler wsHandler) {
        this.wsHandler = wsHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler, "/patient/location").setAllowedOrigins("*");
    }

}
