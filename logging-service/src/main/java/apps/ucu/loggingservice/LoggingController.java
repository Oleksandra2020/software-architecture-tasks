package apps.ucu.loggingservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import apps.ucu.facadeservice.Message;

@RestController
public class LoggingController {
    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private Map<UUID, String> messages = new ConcurrentHashMap<>();

    @GetMapping("/log")
    public String getLog() {
        return messages.values().toString();
    }

    @PostMapping("/log")
    public ResponseEntity<Void> log(@RequestBody Message msg) {
        logger.info("id: " + msg.getId() + ", text: " + msg.getText());
        messages.put(msg.getId(), msg.getText());
        return ResponseEntity.ok().build();
    }
}
