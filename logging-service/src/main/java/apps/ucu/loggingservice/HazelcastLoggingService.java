package apps.ucu.loggingservice;

import org.springframework.stereotype.Service;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.UUID;

@Service
public class HazelcastLoggingService {

    HazelcastInstance hzInstance1 = Hazelcast.newHazelcastInstance();
    IMap<UUID, String> messages = hzInstance1.getMap("messages");

    public void log(Message msg) {
        messages.put(msg.getId(), msg.getText());
    }

    public String getLog() {
        return messages.values().toString();
    }
}
