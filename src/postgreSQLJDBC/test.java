package postgreSQLJDBC;

import java.util.ArrayList;

public class test {

	
	public static void main(String[] args) throws Exception{
		PostgreSQLConnection.setupConnection();
		RouteFinderClass rc1 = new RouteFinderClass();
		rc1.setPosition(-99.60315,30.34206,-98.03035,29.66635);
		ArrayList<RouteData> routeDatas = rc1.run(1, 1);

		for(int i = 0; i<routeDatas.size(); i++){
			double arr[][] = routeDatas.get(i).getGeomAsArray();
			for(int j = 0; j<arr.length; j++){
				//System.out.print(arr[j][0] + "," + arr[j][1] + ",");
			}
		}
		
		DirectionFinder dFinder = new DirectionFinder();
		dFinder.findDirection(routeDatas);
		//RouteFinderClass rc2 = new RouteFinderClass();
		//rc2.setPosition(-100.900, 32.000, -101.900, 33.900);
		//rc2.run(1, 2);
	}
	
	
}
