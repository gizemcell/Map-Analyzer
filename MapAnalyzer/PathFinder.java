import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class PathFinder {
    String startPoint;
    String endPoint;
    ArrayList<Connection> connections;
    private float totalDistance;
    public float getTotalDistance() {
        return totalDistance;
    }
    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }
    public PathFinder(ArrayList<Connection> connections, String startPoint, String endPoint){
        this.connections = connections;
        this.startPoint =startPoint;
        this.endPoint = endPoint;
    }
    /**
     * @return ArrayList<Integer> : the IDs used to find the fastest route.
     */
    public ArrayList<Integer> findFastestPath(){
        HashMap<String,Nodes> nodesMap=new HashMap<>(); // Map to store the shortest path to each node
        ArrayList<Nodes> visitedNodes=new ArrayList<>(); // List of nodes to be visited
        // Initialize the visited nodes with the starting point connections
        for(Connection item: connections){
            if(item.getPoint1().equals(startPoint) ){
                Nodes nude=new Nodes(item.getPoint1(), item.getPoint2(), item.getDifference(),item.isReverse());
                nude.getRoad().add(item.getId());
                visitedNodes.add(nude);
            }
        }
        Collections.sort(visitedNodes,new RoadComparator());
        // Main loop to process each node
        while (!visitedNodes.isEmpty())
        {
            Nodes currentNode=visitedNodes.remove(0);
            String currentEndPoint= currentNode.getEnd();
            if(nodesMap.containsKey(currentEndPoint)){
                continue;
            }
            nodesMap.put(currentEndPoint,currentNode);
            // Update the distances for the adjacent nodes
            for(Connection item: connections){
                if(item.getPoint1().equals(currentEndPoint)){
                    int updateIndex= findNodeIndex(item.getPoint2(),visitedNodes);
                    if(updateIndex != -1){
                        // Update the distance if a shorter path is found
                        if(currentNode.getDistance()+item.getDifference()<visitedNodes.get(updateIndex).getDistance()){
                            visitedNodes.get(updateIndex).setBeginning(currentEndPoint);
                            visitedNodes.get(updateIndex).reSetDistance(currentNode.getDistance()+item.getDifference());
                            visitedNodes.get(updateIndex).reCreateRoad(item.getId());
                        }
                    }
                    else{
                        Nodes nude=new Nodes(currentEndPoint, item.getPoint2(), currentNode.getDistance()+item.getDifference(),item.isReverse());
                        nude.getRoad().add(item.getId());
                        visitedNodes.add(nude);
                    }
                }
            }
            Collections.sort(visitedNodes,new RoadComparator());
        }
        // Build the final path from the end point to the start point
        ArrayList<Integer> path=new ArrayList<>();
        String currentPathPoint= endPoint;
        totalDistance=nodesMap.get(endPoint).getDistance();
        while(!currentPathPoint.equals(startPoint)){
            path.add(nodesMap.get(currentPathPoint).getRoad().get(0));
            currentPathPoint=nodesMap.get(currentPathPoint).getBeginning();
        }
        Collections.reverse(path);
        return path;

    }
    public ArrayList<Integer> findBarelyConnection(String output){
        HashMap<String, Connection> connectionMap=new HashMap<>();
        ArrayList<Connection> visitedConnections=new ArrayList<>();
        ArrayList<Connection> tempConnections=new ArrayList<>(); // Temporary list for reversed connections
        ArrayList<Connection> allConnections=(ArrayList<Connection>) connections.clone();
        /**
         * Add reversed connections to handle undirected edges.The start and end points will no longer be fixed.
          */
        for(Connection p:allConnections){
            if(p.getPoint1().equals(startPoint) || p.getPoint2().equals(endPoint)){
                Connection reversedConnection =new Connection(p.getPoint2(),p.getPoint1(),p.getDifference(),p.getId(),true);
                tempConnections.add(reversedConnection);
            }
        }
        allConnections.addAll(tempConnections); // Add the reversed connections to the main list
        ArrayList<String> uniquePoints= getUniquePoints(allConnections);
        int totalPoints=uniquePoints.size();
        String currentStartPoint=uniquePoints.get(0); //alphabetically smallest value.
        /**
         * Start by adding all roads connected to the currentStartPoint to the visitedConnections list.
         */
        for(Connection item:allConnections){
            if(item.getPoint1().equals(currentStartPoint)){
                visitedConnections.add(item);
            }
        }
        Collections.sort(visitedConnections,new ConnectionDistanceComparator());
        ArrayList<String> detectedPoints=new ArrayList<>(); //stores previously visited points.
        detectedPoints.add(currentStartPoint);
        while(connectionMap.size()<totalPoints-1){
            Connection currentConnection=visitedConnections.remove(0);
            String newBegin=currentConnection.getPoint1();
            String newEnd=currentConnection.getPoint2();
            String newPoint =detectedPoints.contains(newBegin) ? newEnd:newBegin;
            if(detectedPoints.contains(newPoint)){
                continue;
            }
            connectionMap.put(newPoint,currentConnection);
            detectedPoints.add(newPoint);
            for(Connection item:allConnections){
                if(item.getPoint1().equals(newPoint) && !detectedPoints.contains(item.getPoint2())){
                    visitedConnections.add(item);
                }
            }
            Collections.sort(visitedConnections,new ConnectionDistanceComparator());
        }
        // Build the final path and write to the output file
        ArrayList<Connection> finalPath=new ArrayList<>(connectionMap.values());
        Collections.sort(finalPath,new ConnectionDistanceComparator());
        FileOutput.writeToFile(output,"Roads of Barely Connected Map is:",true,true);
        ArrayList<Integer> pathIds=new ArrayList<>();
        for(Connection s: finalPath){
            pathIds.add(s.getId());
            writeConnectionById(s.getId(),output);
        }
        return pathIds;
    }
    // Helper method to find the index of a node in the visited list
    private int findNodeIndex(String endPoints, ArrayList<Nodes> nodes){
        for(int i =0;i<nodes.size();i++){
            if(nodes.get(i).getEnd().equals(endPoints)){
                return i;
            }
        }
        return -1;
    }
    // Helper method to get the unique points in the map
    private ArrayList<String> getUniquePoints(ArrayList<Connection> connections){
        HashSet<String> uniquePointsSet=new HashSet<>();
        for(Connection connection:connections){
            uniquePointsSet.add(connection.getPoint1());
            uniquePointsSet.add(connection.getPoint2());
        }
        ArrayList<String> uniquePoints=new ArrayList<>(uniquePointsSet);
        Collections.sort(uniquePoints);
        return uniquePoints;
    }
    public void writeConnectionById(int id, String path){
        for(Connection connection: connections){
            if(connection.getId()==id){
                if(connection.isReverse()){
                    FileOutput.writeToFile(path,connection.getPoint2()+"\t"+connection.getPoint1()+"\t"+connection.getDifference()+"\t"+connection.getId(),true,true);
                    break;
                }
                else{
                    FileOutput.writeToFile(path,connection.getPoint1()+"\t"+connection.getPoint2()+"\t"+connection.getDifference()+"\t"+connection.getId(),true,true);
                    break;
                }

            }
        }
    }







}
