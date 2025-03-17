import java.util.ArrayList;

public class Nodes {
    private String beginning;
    private String end;
    private int distance;
    private boolean reverse;
    /**
     *
     * The road stores the IDs of the paths through which the end point is reached.
     */
    private ArrayList<Integer> road;


    public Nodes(String beginning, String end, int distance,boolean reverse) {
        this.beginning = beginning;
        this.end = end;
        this.distance = distance;
        this.reverse=reverse;
        road=new ArrayList<>();
    }
    public String getBeginning() {
        return beginning;
    }

    public void setBeginning(String beginning) {
        this.beginning = beginning;
    }

    public String getEnd() {
        return end;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
    public void reCreateRoad(int id){
        road=new ArrayList<>();
        road.add(id);
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getDistance() {
        return distance;
    }
    public void reSetDistance(int distance){
        this.distance=distance;
    }

    public void setDistance(int distan) {
        this.distance = distance+distan;
    }

    public ArrayList<Integer> getRoad() {
        return road;
    }
    public void setRoad(ArrayList<Integer> road){
        this.road=road;
    }

}
