import com.hazelcast.collection.IQueue;
import com.hazelcast.config.Config;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastMember1_dist_queue {

    public static void main( String[] args ) throws Exception {
        Config config = new Config();
        QueueConfig queueConfig = config.getQueueConfig("default");
        queueConfig.setName("queue")
                .setMaxSize(100)
                .setStatisticsEnabled(true)
                .setSplitBrainProtectionName("splitbrainprotectionname");
        config.addQueueConfig(queueConfig);

        HazelcastInstance hz1 = Hazelcast.newHazelcastInstance(config);
        IQueue<Integer> queue = hz1.getQueue( "queue" );
        for ( int k = 1; k < 1000; k++ ) {
            queue.put( k );
            System.out.println( "Producing: " + k );
            Thread.sleep(1000);
        }
        queue.put( -1 );
        System.out.println( "Producer Finished!" );
    }
}
