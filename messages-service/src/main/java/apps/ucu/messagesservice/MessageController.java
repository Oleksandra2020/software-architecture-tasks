package apps.ucu.messagesservice;

import com.hazelcast.org.codehaus.commons.nullanalysis.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MessageController {

    private final HazelcastMessageService ms;
    Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageController(@RequestBody @NotNull HazelcastMessageService ms) {
        this.ms = ms;
    }

    @GetMapping("/messages")
    public String getMessages() throws Exception {
        return ms.getMessages();
    }

    @PostMapping("/messages")
    public ResponseEntity<Void> message(@RequestBody Message msg) throws Exception {
        logger.info("id: " + msg.getId() + " msg: " + msg.getText());
        return ms.message(msg);
    }

}
