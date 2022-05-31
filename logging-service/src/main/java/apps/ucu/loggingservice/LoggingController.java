package apps.ucu.loggingservice;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {
    Logger logger = LoggerFactory.getLogger(LoggingController.class);
    private final HazelcastLoggingService hazelcastLoggingService;

    public LoggingController(@RequestBody @NotNull HazelcastLoggingService hazelcastLoggingService) {
        this.hazelcastLoggingService = hazelcastLoggingService;
    }

    @GetMapping("/log")
    public String getLog() {
        return hazelcastLoggingService.getLog();
    }

    @PostMapping("/log")
    public ResponseEntity<Void> log(@RequestBody Message msg) {
        hazelcastLoggingService.log(msg);
        logger.info("id: " + msg.getId() + " msg: " + msg.getText());
        return ResponseEntity.ok().build();
    }
}
