import java.util.Comparator;

public class RoadComparator implements Comparator<Nodes> {

    @Override
    public int compare(Nodes o1, Nodes o2) {
        if(o1.getDistance()>o2.getDistance()){
            return 1;
        }
        else if(o1.getDistance()<o2.getDistance()){
            return -1;
        }
        else{
            return o1.getRoad().get(0) < o2.getRoad().get(0) ? -1:1;
        }
    }
}
