package postgreSQLJDBC;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DirectionFinder {
	ArrayList<JunctionPoints> junctionPoints = new ArrayList<>();
	public ArrayList<JunctionPoints> findDirection(ArrayList<RouteData> routeSegmentData) throws Exception{
		double[] prevPoint = new double[2];
		double[] nextPoint = new double[2];
		double temp[][] = routeSegmentData.get(0).getGeomAsArray();
		prevPoint[0] = temp[temp.length-2][0];
		prevPoint[1] = temp[temp.length-2][1];
		for(int i = 1; i<routeSegmentData.size(); i++){
			JunctionPoints jPoint = new JunctionPoints();
			
			int nextRouteIndex = -1;
			int prevRouteIndex = -1;
			int routeCount = 0;
			//System.out.println("for i = " + i);
			temp = routeSegmentData.get(i).getGeomAsArray();
			jPoint.setX(temp[0][0]);
			jPoint.setY(temp[0][1]);
			nextPoint[0] = temp[1][0];
			nextPoint[1] = temp[1][1];
			//System.out.println("source = " + routeSegmentData.get(i).getSource());	
			//System.out.println("main point : " + jPoint.getX() + " " + jPoint.gety());
			//System.out.println("next point : " + nextPoint[0] + " " + nextPoint[1]);
			//System.out.println("prev point : " + prevPoint[0] + " " + prevPoint[1]);
			ArrayList<Double> routePointX = new ArrayList<>(); 
			ArrayList<Double> routePointY = new ArrayList<>(); 
			ResultSet rCount = PostgreSQLConnection.getRouteCount(routeSegmentData.get(i).getSource());
			
			while(rCount.next()){
				//System.out.println(rCount.getString(2));
				double t[] = BasicMath.getPointFromGeom(rCount.getString(2)); 
				routePointX.add(t[0]);
				routePointY.add(t[1]);
				routeCount++;
				if(nextPoint[0] == t[0] && nextPoint[1] == t[1]){
					nextRouteIndex = routeCount-1;	
				}
				if(prevPoint[0] == t[0] && prevPoint[1] == t[1]){
					prevRouteIndex = routeCount-1;	
				}
			}
			if(routeCount == 2){
				//System.out.println("Straight road with 2 segments.");
				jPoint.setDirection("#s");
				prevPoint[0] = temp[temp.length-2][0];
				prevPoint[1] = temp[temp.length-2][1];
				
				junctionPoints.add(jPoint);
				continue;
			}
			
			if(nextRouteIndex <= -1){
				System.out.println("something wrong with next route index");
				return null;
			}
			if(prevRouteIndex <= -1){
				System.out.println("something wrong with prev route index");
				return null;
			}
			//System.out.println("next route index = " + nextRouteIndex);
			//System.out.println("prev route index = " + prevRouteIndex);
			//System.out.println("route count = " + routeCount);
			double angleOfMainLine = BasicMath.getAngle(prevPoint[0], prevPoint[1], jPoint.getX(), jPoint.getY());
			double angleRadian = angleOfMainLine * Math.PI / 180;
			//System.out.println("angle main = " + angleOfMainLine);
			double [] anglesOfRoutes = new double[routeCount];
			
			for(int j=0; j<routeCount; j++){
				//System.out.print("route no = " + (j+1));
				if(j == prevRouteIndex){
					//System.out.println(" previous Route.");
					anglesOfRoutes[j] = 0;
					continue;
				}
				double x3_dash = routePointX.get(j) - jPoint.getX();
				double y3_dash = routePointY.get(j) - jPoint.getY();
				double x3_double_dash = (x3_dash*Math.cos(angleRadian)) + (y3_dash*Math.sin(angleRadian));
				double y3_double_dash = (y3_dash*Math.cos(angleRadian)) - (x3_dash*Math.sin(angleRadian));

				anglesOfRoutes[j] = BasicMath.getAngle(0,0, x3_double_dash, y3_double_dash);
				//System.out.println(" angle local " + anglesOfRoutes[j]);

			}


			int leftRotues = 0;
			double tolerance = RegularConstants.DIRECTION_TOLERENCE;
			boolean isStraight = false;
			String dir = "";
			for(int k = 0; k<routeCount; k++){
				if(k == prevRouteIndex || k == nextRouteIndex){
					continue;
				}
				if(anglesOfRoutes[nextRouteIndex] < anglesOfRoutes[k]){
					leftRotues++;
				}
				if(Math.abs(anglesOfRoutes[nextRouteIndex]) <= tolerance && 
						Math.abs(anglesOfRoutes[nextRouteIndex]) < Math.abs(anglesOfRoutes[k])){
					isStraight = true;
				}
			}
			//System.out.println("left routes : " + leftRotues);
			if(routeCount == 3){
				//System.out.println("2 roads ahead");
				
				if(leftRotues == 0){
					dir = "Left";
				}
				else if(leftRotues == 1){
					dir = "Right";
				}
			}
			else if(routeCount == 4){
				//System.out.println("3 roads ahead");
				
				if(leftRotues == 0){
					dir = "Left";
				}
				else if(leftRotues == 1){
					dir = "2nd_Exit";
				}
				else{
					dir = "Right";
				}
			}
			else if(routeCount > 4){
				//System.out.println((routeCount-1) + " roads ahead");
				String suf = "th";
				if(leftRotues == 0)
					suf = "st";
				else if(leftRotues == 1)
					suf = "nd";
				dir = (leftRotues+1) + suf + "_Exit";
			}
			
			if(Math.abs(anglesOfRoutes[nextRouteIndex]) <= tolerance && isStraight){
				//System.out.println("go straight");
				jPoint.setDirection("Straight");
			}
			else{
				//System.out.println("take " + dir);
				jPoint.setDirection(dir);
			}


			prevPoint[0] = temp[temp.length-2][0];
			prevPoint[1] = temp[temp.length-2][1];
			
			junctionPoints.add(jPoint);
		}
		
		
		for(int i = 0; i<junctionPoints.size(); i++){
			System.out.println(i + ") " + junctionPoints.get(i).getX() 
					+ " " + junctionPoints.get(i).getY() 
					+ " " + junctionPoints.get(i).getDirection());
		}
		
		return junctionPoints;
	}
}
