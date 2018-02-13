package postgreSQLJDBC;

import java.sql.ResultSet;
import java.util.ArrayList;



public class RouteFinderClass {

	private double x1,y1,x2,y2;
	private ResultSet route; 
	private Location source, destination;
	private ArrayList<RouteData> routeSegmentsData;
	
	
	public static void main(String[] args) throws Exception{
		
		RouteFinderClass runnerClass = new RouteFinderClass();
		runnerClass.setPosition(-100.100,32.900,-102.500,33.600);
		runnerClass.run(1, 1);
		
	}
	
	public ArrayList<RouteData> run(int uid, int rid) throws Exception{
		// source location setup
		source = new Location(x1, y1);
		source.getLocationData();
		
		// destination location setup
		destination = new Location(x2, y2);
		destination.getLocationData();
		
		// finding the route
		route = PostgreSQLConnection.getRoute(source.getNearestNode(), destination.getNearestNode());
		if(!route.isBeforeFirst()){
			System.out.print("There is no data in the Route resultset.");
			
		}
		
		// save route data as array of object
		routeSegmentsData = saveDataFrom(route, uid, rid);
		
		// remove overlapped route section.
		// overlapped route sections can be present at
		// first or at last position.
		// If the source/destination location is in between 
		// source and target of the route section then route is overlapped.
		removeOverlapRoute(routeSegmentsData.get(0), source);
		removeOverlapRoute(routeSegmentsData.get(routeSegmentsData.size()-1), destination);
		
		// add new route sub section at the start and at the end of the route
		addNewRoutePart(source, true, uid, rid);
		addNewRoutePart(destination, false, uid, rid);
		
		// Update sequence no
		updateSequenceNo();
		
		for(int i = 0; i<routeSegmentsData.size(); i++){
			double arr[][] = routeSegmentsData.get(i).getGeomAsArray();
			for(int j = 0; j<arr.length; j++){
				//System.out.println(j + " " + arr[j][0] + "," + arr[j][1]+ ",");
			}
		}
		for(int i = 0; i<routeSegmentsData.size(); i++){
			double arr[][] = routeSegmentsData.get(i).getGeomAsArray();
			for(int j = 0; j<arr.length; j++){
				//System.out.println(arr[j][0] + "," + arr[j][1]+ ",");
			}
		}
		
		return routeSegmentsData;
	}	
	
	private void addNewRoutePart(Location location, boolean isSource, int uid, int rid) throws Exception{
		//get geom as double array
		double geomAsArray[][] = location.getGeomArray();
		
		// choose appropriate part to joint.
		int partNo = getEdgeGeomNo(geomAsArray, isSource);
		
		// get the split index for source location increase the tolerance if index is -1
		int splitIndex = 0;
		int i = 1;
		do{
			splitIndex = getSplitIndex(geomAsArray, location.getNearestEdge().getxNearby(), location.getNearestEdge().getyNearby(), RegularConstants.ROUTE_TOLERENCE * i);
			i *= 10;
		}while(splitIndex<0 || i == 1000000);
		if(i == 1000000){
			System.out.println("value of tolerence is crossed the limit");
			return;
		}
		//System.out.println("part no " + partNo);
		//System.out.println("splitindex = " + splitIndex);
		// get the part
		if(partNo != 0){
			double partGeom[][] = getEdgePartGeom(location, geomAsArray, splitIndex, partNo, isSource);
			double partLength = BasicMath.roundOff(BasicMath.getSectionLength(partGeom), 3);
			
			//reverse the part for some reasons
			if((isSource && partNo == 1) || (!isSource && partNo==2))
			partGeom = BasicMath.reverse(partGeom);
			
			// add new part to the routeDatas
			int index = 0;
			if(!isSource)
				index = routeSegmentsData.size()-1;
			RouteData e = new RouteData();
			e.setSeq(1);
			if(isSource){
				e.setSource(0);
				e.setTarget(routeSegmentsData.get(index).getSource());
			}
			else{
				e.setSource(routeSegmentsData.get(index).getTarget());
				e.setTarget(0);
			}
			e.setTraffic(location.getNearestEdge().getTraffic());
			e.setLength(partLength);
			e.setGid(-1);
			e.setGeomFromArray(partGeom);
			e.setUserId(uid);
			e.setRouteId(rid);
			e.getArrayFromGeom();

			if(isSource)
				index--;
			routeSegmentsData.add(index+1, e);
		}

	}

	private void updateSequenceNo() {
		for(int i = 1; i<=routeSegmentsData.size(); i++){
			routeSegmentsData.get(i-1).setSeq(i);
		}
	}

	private double[][] getEdgePartGeom(Location edge, double[][] edgeGeomArray, int splitIndex, int partNo, boolean isStart) {
		double [][] partGeom = null;
		if(partNo == 1){
			partGeom = new double[splitIndex+2][2];
			for(int i = 0; i<partGeom.length; i++){
				if(i == splitIndex+1){
					partGeom[i][0] = edge.getNearestEdge().getxNearby();
					partGeom[i][1] =edge.getNearestEdge().getyNearby();
					continue;
				}
				partGeom[i] = edgeGeomArray[i];
			}
		}
		
		else if(partNo == 2){
			partGeom = new double[edgeGeomArray.length-splitIndex][2];
			for(int i = 0; i<partGeom.length; i++){
				if(i == 0){
					partGeom[i][0] = edge.getNearestEdge().getxNearby();
					partGeom[i][1] = edge.getNearestEdge().getyNearby();
					continue;
				}
				partGeom[i] = edgeGeomArray[i + splitIndex];
			}
		}
		return partGeom;
	}

	private int getEdgeGeomNo(double[][] geom, boolean isStart) {
		int index = 0;
		if(!isStart)
			index = routeSegmentsData.size()-1;
		double route[][] = routeSegmentsData.get(index).getGeomAsArray();
		
		index = 0;
		if(!isStart)
			index =  route.length-1;
		if(route[index][0] == geom[0][0] && route[index][1] == geom[0][1]){
			return 1;
		}
		if(route[index][0] == geom[geom.length-1][0] && route[index][1] == geom[geom.length-1][1]){
			return 2;
		}
		return 0;
	}

	private int getSplitIndex(double[][] geomArray, double x, double y, double tolerence) throws Exception{
		int splitIndex = -1;
		for(int i = 0; i<geomArray.length-1; i++){
	    	// line : - 1-----3-----2 like wise
	    	
	    	double dis13 = BasicMath.getLength(geomArray[i][0], geomArray[i][1], x, y);
	    	double dis23 = BasicMath.getLength(geomArray[i+1][0], geomArray[i+1][1], x, y);
	    	double dis12 = BasicMath.getLength(geomArray[i+1][0], geomArray[i+1][1], geomArray[i][0], geomArray[i][1]);
	    	
	    	// if 3 is on line then
	    	if(Math.abs(dis13 + dis23 - dis12) <= tolerence){
	    		splitIndex = i;
	    		break;
	    	}
	    }
		return splitIndex;
	}

	private ArrayList<RouteData> saveDataFrom(ResultSet route, int uid, int rid) {
		ArrayList<RouteData> arrayList = new ArrayList<>();
		try{
			while(route.next()){
				RouteData e = new RouteData();
				e.setSeq(route.getInt(DijkstraIndex.SEQ));
				e.setSource(route.getInt(DijkstraIndex.SOURCE));
				e.setTarget(route.getInt(DijkstraIndex.TARGET));
				e.setTraffic(route.getInt(DijkstraIndex.TRAFFIC));
				e.setLength(route.getDouble(DijkstraIndex.LEN));
				e.setGid(route.getInt(DijkstraIndex.GID));
				e.setGeom(route.getString(DijkstraIndex.GEOM));
				e.setUserId(uid);
				e.setRouteId(rid);
				e.getArrayFromGeom();
				arrayList.add(e);
			}
			return arrayList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private void removeOverlapRoute(RouteData rData, Location location)throws Exception{
		if(rData.getSource() == location.getNearestEdge().getSource()){
			if(rData.getTarget() == location.getNearestEdge().getTarget()){
				routeSegmentsData.remove(rData.getSeq()-1);
				//System.out.println("\nTarget Node deleted");
			}
		}
		else if(rData.getSource() == location.getNearestEdge().getTarget()){
			if(rData.getTarget() == location.getNearestEdge().getSource()){
				routeSegmentsData.remove(rData.getSeq()-1);
				//System.out.println("\nSource Node deleted");
			}
		}
	}

	public void setPosition(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

}
