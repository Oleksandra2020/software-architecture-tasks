import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastMember4_dist_queue {

    public static void main( String[] args ) throws Exception {
        HazelcastInstance hz4 = Hazelcast.newHazelcastInstance();
        IQueue<Integer> queue = hz4.getQueue( "queue" );
        while ( true ) {
            int item = queue.take();
            System.out.println( "Consumed: " + item );
            if ( item == -1 ) {
                queue.put( -1 );
                break;
            }
            Thread.sleep( 5000 );
        }
        System.out.println( "Consumer Finished!" );
    }
}
