package apps.ucu.loggingservice;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Message {
    @Getter @Setter
    UUID id;
    @Getter @Setter
    String text;

    public Message(UUID id, String text) {
        this.id = id;
        this.text = text;
    }
}
