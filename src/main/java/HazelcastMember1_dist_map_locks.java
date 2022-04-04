import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.Objects;

public class HazelcastMember1_dist_map_locks {

    public static void main( String[] args ) throws Exception {
        HazelcastInstance hz1 = Hazelcast.newHazelcastInstance();
        IMap<String, Value> map = hz1.getMap( "map" );
        String key = "1";
        map.put( key, new Value() );
        System.out.println( "Starting" );

        String mode = args[0];

        if (Objects.equals(mode, "none")) {
            for ( int k = 0; k < 1000; k++ ) {
                if ( k % 100 == 0 ) System.out.println( "At: " + k );
                Value value = map.get( key );
                Thread.sleep( 10 );
                value.amount++;
                map.put( key, value );
            }
        } else if (Objects.equals(mode, "pessimistic")) {
            for ( int k = 0; k < 1000; k++ ) {
                if ( k % 10 == 0 ) System.out.println( "At: " + k );
                map.lock( key );
                try {
                    Value value = map.get( key );
                    Thread.sleep( 10 );
                    value.amount++;
                    map.put( key, value );
                } finally {
                    map.unlock( key );
                }
            }
        } else if (Objects.equals(mode, "optimistic")) {
            for ( int k = 0; k < 1000; k++ ) {
                if ( k % 10 == 0 ) System.out.println( "At: " + k );
                for (; ; ) {
                    Value oldValue = map.get( key );
                    Value newValue = new Value( oldValue );
                    Thread.sleep( 10 );
                    newValue.amount++;
                    if ( map.replace( key, oldValue, newValue ) )
                        break;
                }
            }
        }

        System.out.println( "Finished! Result = " + map.get(key).amount );
    }

}
