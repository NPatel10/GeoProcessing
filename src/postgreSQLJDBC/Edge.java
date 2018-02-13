package postgreSQLJDBC;

 
public class Edge {
	private int gid;
	private int source,target, traffic;
	private double xNearby, yNearby;
	private double length, dis;
	private String geom;
	
	public Edge(int gid, int source, int target, int traffic, double xNearBy, double yNearBy, double length, double dis, String geom){
		this.gid = gid;
		this.source = source;
		this.target = target;
		this.traffic = traffic;
		this.xNearby = xNearBy;
		this.yNearby = yNearBy;
		this.length = length;
		this.dis = dis;
		this.geom = geom;
	}
	
	public int getGid() {  
	    return gid;  
	}  
	public void setGid(int id) {  
	    this.gid = id;  
	}  
	public int getSource() {  
	    return source;  
	}  
	public void setSource(int source) {  
	    this.source = source;  
	}  
	public int getTarget() {  
	    return target;  
	}  
	public void setTarget(int target) {  
	    this.target = target;  
	}  
	
	public double getxNearby() {
		return xNearby;
	}
	public void setxNearby(double xNearby) {
		this.xNearby = xNearby;
	}
	public double getyNearby() {
		return yNearby;
	}
	public void setyNearby(double yNearby) {
		this.yNearby = yNearby;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public int getTraffic() {
		return traffic;
	}
	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}
	public double getDis() {
		return dis;
	}
	public void setDis(double dis) {
		this.dis = dis;
	}
	public String toString(){
		return "Details of Mapdata Object "+ this.hashCode() + "\nGid = " + getGid() + "\nSource = " + getSource() + "\nTarget = " + getTarget();
		
	}
}
