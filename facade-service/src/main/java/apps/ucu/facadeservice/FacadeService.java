package apps.ucu.facadeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FacadeService {
    Logger logger = LoggerFactory.getLogger(FacadeService.class);


    @Autowired
    private DiscoveryClient discoveryClient;

    public List<String> getLoggingUris() {
        List<ServiceInstance> list = discoveryClient.getInstances("logging-service");
        List<String> loggingUris = new ArrayList<>();
        for (ServiceInstance serviceInstance : list) {
            loggingUris.add(serviceInstance.getUri().toString());
        }
        return loggingUris;
    }

    public List<String> getMesssagesUris() {
        List<ServiceInstance> list = discoveryClient.getInstances("messages-service");
        List<String> messageUris = new ArrayList<>();
        for (ServiceInstance serviceInstance : list) {
            messageUris.add(serviceInstance.getUri().toString());
        }
        return messageUris;
    }

    private String getRandomUri(List<String> uris) {
        Random rand = new Random();
        return uris.get(rand.nextInt(uris.size()));
    }

    public String getFacade(RestTemplate restTemplate) {
        String loggingUri = getRandomUri(getLoggingUris());
        String messageUri = getRandomUri(getMesssagesUris());
        logger.info("Random logging service: " + loggingUri);
        logger.info("Random message service: " + messageUri);
        ResponseEntity<String> logMessage = restTemplate.getForEntity(loggingUri + "/log", String.class);
        String logResponse = logMessage.getBody();

        ResponseEntity<String> mesMessage = restTemplate.getForEntity(messageUri + "/messages", String.class);
        String mesResponse = mesMessage.getBody();

        return logResponse + " " + mesResponse;
    }

    public List<ResponseEntity> postFacade(RestTemplate restTemplate, String text) throws InterruptedException {
        Message msg = new Message(UUID.randomUUID(), text);
        String loggingUri = getRandomUri(getLoggingUris());
        String messageUri = getRandomUri(getMesssagesUris());
        logger.info("Random logging service: " + loggingUri);
        logger.info("Random message service: " + messageUri);
        List<ResponseEntity> responseValues = Arrays.asList(
                restTemplate.postForObject(messageUri + "/messages", msg, ResponseEntity.class),
                restTemplate.postForObject(loggingUri + "/log", msg, ResponseEntity.class)
        );

        return responseValues;
    }

}
