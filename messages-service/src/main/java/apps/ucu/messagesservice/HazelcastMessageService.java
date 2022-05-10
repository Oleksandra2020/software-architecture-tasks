package apps.ucu.messagesservice;

import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class HazelcastMessageService {

    HazelcastInstance hz = Hazelcast.newHazelcastInstance();
    private IQueue<String> queue = hz.getQueue( "queue" );


    public String getMessages() throws Exception {
        StringBuilder items = new StringBuilder();
        while (!queue.isEmpty()) {
            String item = queue.take();
            items.append(item);
            items.append(" ");
        }
        return items.toString();
    }

    public ResponseEntity<Void> message(Message msg) throws Exception {
        queue.put(msg.getText());
        return ResponseEntity.ok().build();
    }
}
