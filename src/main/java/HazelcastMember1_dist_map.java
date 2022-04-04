import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class HazelcastMember1_dist_map {

    public static void main(String[] args) {
        HazelcastInstance hzInstance1 = Hazelcast.newHazelcastInstance();

        IMap<Integer, String> distributedMap = hzInstance1.getMap("my-distributed-map");

        for(int i = 0; i < 1000; i++) {
            distributedMap.put(i, "value");
        }

    }

}
