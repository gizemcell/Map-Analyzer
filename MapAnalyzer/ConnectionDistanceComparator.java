import java.util.Comparator;

public class ConnectionDistanceComparator implements Comparator<Connection> {
    @Override
    public int compare(Connection o1, Connection o2) {
        if(o1.getDifference()>o2.getDifference()){
            return 1;
        }
        else if(o1.getDifference()<o2.getDifference()){
            return -1;
        }
        else{
            return o1.getId() < o2.getId() ? -1:1;
        }
    }
}
