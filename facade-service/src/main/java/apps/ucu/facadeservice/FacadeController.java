package apps.ucu.facadeservice;

import com.sun.istack.internal.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
public class FacadeController {

    String loggingUri = "http://localhost:8081/log";
    String messagesUri = "http://localhost:8082/messages";

    @GetMapping("/facade-service")
    public String getFacade(@NotNull RestTemplate restTemplate) {
        ResponseEntity<String> logMessage = restTemplate.getForEntity(loggingUri, String.class);
        String logResponse = logMessage.getBody();

        ResponseEntity<String> mesMessage = restTemplate.getForEntity(messagesUri, String.class);
        String mesResponse = mesMessage.getBody();

        return logResponse + " " + mesResponse;
    }

    @PostMapping("/facade-service")
    public ResponseEntity postFacade(@NotNull RestTemplate restTemplate, @RequestBody String txt) {
        Message msg = new Message(UUID.randomUUID(), txt);
        return restTemplate.postForObject(loggingUri, msg, ResponseEntity.class);
    }

}
