package postgreSQLJDBC;

import java.sql.ResultSet;



public class Location {
	private double x, y;
	private int nearestNode = -1;
	private ResultSet nearestNodeList; 
	private Edge nearestEdge;

	
	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void getLocationData() throws Exception{
		ResultSet rs = PostgreSQLConnection.getNearestEdge(this.x, this.y);
		nearestNodeList = PostgreSQLConnection.getNearestNodes(this.x, this.y);
		rs.next();
		nearestEdge = new Edge(rs.getInt(EdgeIndex.GID), 
				rs.getInt(EdgeIndex.SOURCE), 
				rs.getInt(EdgeIndex.TARGET),
				rs.getInt(EdgeIndex.TRAFFIC),
				rs.getDouble(EdgeIndex.X),
				rs.getDouble(EdgeIndex.Y), 
				rs.getDouble(EdgeIndex.LENGTH), 
				rs.getDouble(EdgeIndex.DISTANCE), 
				rs.getString(EdgeIndex.GEOM));
		this.computeNearestNode();
	}
	
	private void computeNearestNode() {
		try{
			//nearestEdge.next();
			while(nearestNodeList.next()){
				if(nearestEdge.getSource() == nearestNodeList.getInt(1) || 
						nearestEdge.getTarget() == nearestNodeList.getInt(1)){
					nearestNode = nearestNodeList.getInt(1);
					return;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		nearestNode = -1;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public int getNearestNode() {
		return nearestNode;
	}

	public Edge getNearestEdge() {
		return nearestEdge;
	}
	
	public double[][] getGeomArray() throws Exception{
		String geom = nearestEdge.getGeom();
		String str = geom.substring(17, geom.length()-2);
		String arry[] = str.split(",");
		double coordinates[][] = new double[arry.length][2];
		for(int i = 0; i<arry.length; i++){
			String latlon[] = arry[i].split(" ");
			coordinates[i][0] = Double.parseDouble(latlon[0]);
			coordinates[i][1] = Double.parseDouble(latlon[1]);
		}
		return coordinates;
	}
}
