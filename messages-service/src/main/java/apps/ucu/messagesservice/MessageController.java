package apps.ucu.messagesservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/messages")
    public String getMessage() {
        return "messages-service is not implemented yet.";
    }
}
