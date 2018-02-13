package postgreSQLJDBC;

public class RouteData {
	private int userId, routeId, seq, traffic, source, target, gid;
	private double length;
	private String geom;
	private double[][] geomAsArray;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getTraffic() {
		return traffic;
	}

	public void setTraffic(int traffic) {
		this.traffic = traffic;
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

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}
	
	public void getArrayFromGeom(){
		String str = geom.substring(17, geom.length()-2);
		String arry[] = str.split(",");
		geomAsArray = new double[arry.length][2];
		for(int i = 0; i<arry.length; i++){
			String latlon[] = arry[i].split(" ");
			geomAsArray[i][0] = Double.parseDouble(latlon[0]);
			geomAsArray[i][1] = Double.parseDouble(latlon[1]);
		}
	}
	public void setGeomFromArray(double arr[][]){
		String s = "MULTILINESTRING((";
		for(int i = 0; i<arr.length; i++){
			s += String.valueOf(arr[i][0]) + " " + String.valueOf(arr[i][1]);
			if(i!=arr.length-1)
				s += ",";
		}
		s += "))";
		setGeom(s);
	}

	public double[][] getGeomAsArray() {
		return geomAsArray;
	}
	
}
