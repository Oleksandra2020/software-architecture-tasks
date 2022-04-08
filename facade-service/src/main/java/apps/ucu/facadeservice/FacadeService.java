package apps.ucu.facadeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class FacadeService {
    private List<String> loggingUris;
    private String messagesUri;
    Logger logger = LoggerFactory.getLogger(FacadeService.class);;

    public FacadeService() {
        loggingUris = Arrays.asList("http://localhost:8082/log","http://localhost:8083/log", "http://localhost:8084/log");
        messagesUri = "http://localhost:8081/messages";
    }

    private String getRandomUri() {
        Random rand = new Random();
        return loggingUris.get(rand.nextInt(loggingUris.size()));
    }

    public String getFacade(RestTemplate restTemplate) {
        String loggingUri = getRandomUri();
        logger.info("Random logging service: " + loggingUri);
        ResponseEntity<String> logMessage = restTemplate.getForEntity(loggingUri, String.class);
        String logResponse = logMessage.getBody();

        ResponseEntity<String> mesMessage = restTemplate.getForEntity(messagesUri, String.class);
        String mesResponse = mesMessage.getBody();

        return logResponse + " " + mesResponse;
    }

    public ResponseEntity postFacade(RestTemplate restTemplate, String text) {
        Message msg = new Message(UUID.randomUUID(), text);
        String loggingUri = getRandomUri();
        logger.info("Random logging service: " + loggingUri);
        return restTemplate.postForObject(loggingUri, msg, ResponseEntity.class);
    }

}
