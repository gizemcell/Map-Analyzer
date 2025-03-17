import java.util.ArrayList;

public class PathProcessor {
    public static void processPaths(String[] inputContent, String outputFilePath) {
        FileOutput.writeToFile(outputFilePath,"",false,false);
        float constructionOrigin=0;
        String beginning=inputContent[0].split("\t")[0];
        String end=inputContent[0].split("\t")[1];
        /**
         * The connections hold connection objects, and a connection object contains a start(point1), end(point2), distance, id, and reverse.
         * The reverse variable provides information on whether the start and end points have been swapped.
         *Therefore, while writing to the outputFilePath file, the input data can be preserved accurately.
         */
        ArrayList<Connection> connections =new ArrayList<>();
        for(int i=1;i<inputContent.length;i++){
            String[] splitLine=inputContent[i].split("\t");
            constructionOrigin+=Integer.parseInt(splitLine[2]); //calculating construction value for original map by adding all distance.
            if(splitLine[1].equals(beginning) || splitLine[0].equals(end)){
                // If the road starts or ends at the beginning or end point, reverse the points to maintain order
                Connection connection =new Connection(splitLine[1],splitLine[0],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[3]),true);
                connections.add(connection);
                continue;
            }
            Connection connection =new Connection(splitLine[0],splitLine[1],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[3]),false);
            // If the road is not directly connected to the beginning or end point, create a reverse connection
            if(!(splitLine[0].equals(beginning) || splitLine[1].equals(end))){
                Connection reverseConnection =new Connection(splitLine[1],splitLine[0],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[3]),true); //creating reverse path for the same begin and end point.
                connections.add(reverseConnection);
            }
            connections.add(connection);
        }
        PathFinder findPath=new PathFinder(connections,beginning,end);
        ArrayList<Integer> fastestPath=findPath.findFastestPath(); // fastestPath holds ids of fast route
        float fastestDistance=findPath.getTotalDistance();
        FileOutput.writeToFile(outputFilePath, "Fastest Route from "+beginning+" to "+end+" ("+(int)findPath.getTotalDistance()+" KM):",true,true);
        for(Integer i:fastestPath){
            findPath.writeConnectionById(i.intValue(),outputFilePath); //writing to the outputFilePath by finding path from original map using id
        }
        // Calculate construction material usage for the barely connected map
        float constructionBarelyMap=0;
        ArrayList<Integer> barelyConnectedPath=findPath.findBarelyConnection(outputFilePath);
        ArrayList<Connection> newConnections =new ArrayList<>();
        for(Integer i:barelyConnectedPath){
            boolean added=true;
            for(Connection con: connections){
                if(con.getId()==i.intValue()){
                    newConnections.add(con);
                    if(added){
                        constructionBarelyMap+=con.getDifference();
                        added=false;
                    }
                }
            }
        }
        // Create a new PathFinder object with connections for the barely connected map and find the fastest path
       findPath=new PathFinder(newConnections,beginning,end); //finding path on barely connection map.newPoints is a list that holds new ways for barely connection map.
        ArrayList<Integer> barelyConnectedFastestPath=findPath.findFastestPath();
        float fastestBarelyConnectedDistance= findPath.getTotalDistance();
        FileOutput.writeToFile(outputFilePath, "Fastest Route from "+beginning+" to "+end+" on Barely Connected Map"+" ("+(int)findPath.getTotalDistance()+" KM):",true,true);
        for(Integer i:barelyConnectedFastestPath){
            findPath.writeConnectionById(i.intValue(),outputFilePath);
        }
        // Write analysis results to the outputFilePath file
        FileOutput.writeToFile(outputFilePath,"Analysis:",true,true);
        float fastestRouteRatio =fastestBarelyConnectedDistance/fastestDistance;
        float constructionMaterialUsageRatio= constructionBarelyMap /constructionOrigin;
        FileOutput.writeToFile(outputFilePath,"Ratio of Construction Material Usage Between Barely Connected and Original Map: "+String.format("%.2f",constructionMaterialUsageRatio),true,true);
        FileOutput.writeToFile(outputFilePath,"Ratio of Fastest Route Between Barely Connected and Original Map: "+String.format("%.2f",fastestRouteRatio ),true,false);
    }
}

