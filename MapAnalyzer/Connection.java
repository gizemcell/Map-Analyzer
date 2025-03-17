public class Connection {
    private String point1;
    private String point2;
    private Integer id;
    private int difference;
    private boolean reverse;

    public String getPoint1() {
        return point1;
    }

    public void setPoint1(String point1) {
        this.point1 = point1;
    }

    public String getPoint2() {
        return point2;
    }

    public void setPoint2(String point2) {
        this.point2 = point2;
    }

    public Integer getId() {
        return id;
    }

    /**
     *The isReverse method provides information about whether the positions of the points have been swapped.
     */
    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }


    public Connection(String point1, String point2, int difference, int id, boolean reverse) {
        this.point1 = point1;
        this.point2 = point2;
        this.id = id;
        this.difference=difference;
        this.reverse=reverse;
    }
}
