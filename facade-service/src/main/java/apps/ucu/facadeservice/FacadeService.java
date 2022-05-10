package apps.ucu.facadeservice;

import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
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
    private List<String> messageUris;
    Logger logger = LoggerFactory.getLogger(FacadeService.class);

    HazelcastInstance hz = Hazelcast.newHazelcastInstance();
    private IQueue<String> queue = hz.getQueue( "queue" );

    public FacadeService() {
        loggingUris = Arrays.asList("http://localhost:8083/log", "http://localhost:8084/log", "http://localhost:8085/log");
        messageUris = Arrays.asList("http://localhost:8081/messages", "http://localhost:8082/messages");
    }

    private String getRandomUri(List<String> uris) {
        Random rand = new Random();
        return uris.get(rand.nextInt(uris.size()));
    }

    public String getFacade(RestTemplate restTemplate) {
        String loggingUri = getRandomUri(loggingUris);
        String messageUri = getRandomUri(messageUris);
        logger.info("Random logging service: " + loggingUri);
        logger.info("Random message service: " + messageUri);
        ResponseEntity<String> logMessage = restTemplate.getForEntity(loggingUri, String.class);
        String logResponse = logMessage.getBody();

        ResponseEntity<String> mesMessage = restTemplate.getForEntity(messageUri, String.class);
        String mesResponse = mesMessage.getBody();

        return logResponse + " " + mesResponse;
    }

    public List<ResponseEntity> postFacade(RestTemplate restTemplate, String text) throws InterruptedException {
        Message msg = new Message(UUID.randomUUID(), text);
        String loggingUri = getRandomUri(loggingUris);
        String messageUri = getRandomUri(messageUris);
        logger.info("Random logging service: " + loggingUri);
        logger.info("Random message service: " + messageUri);
        List<ResponseEntity> responseValues = Arrays.asList(
                restTemplate.postForObject(messageUri, msg, ResponseEntity.class),
                restTemplate.postForObject(loggingUri, msg, ResponseEntity.class)
        );

        return responseValues;
    }

}
