package postgreSQLJDBC;

public class BasicMath {
	public static double getAngle(double x1, double y1, double x2, double y2){
		double dx = x2 - x1;
		double dy = y2 - y1;
		double theta = Math.atan2(dy, dx);
		return theta*180/Math.PI;
	}
	
	public static double getLength(double x1, double y1, double x2, double y2){
		double t = (x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2);
		return Math.sqrt(t);
	}
	
	public static double getSectionLength(double arr[][]){
		double num = 0;
		for (int i = 0; i<arr.length-1; i++){
			double length = getLength(arr[i][0], arr[i][1], arr[i+1][0], arr[i+1][1]);
			num += length;
		}
		return num;
	}	
	public static double roundOff(double n, int floatingPoint){
		n *= Math.pow(10, floatingPoint);
		long t = Math.round(n);
		n = (double) t / Math.pow(10, floatingPoint);
		return n;
	}
	
	public static double[][] geomAsArray(String s){
		String str = s.substring(17, s.length()-2);
		String arry[] = str.split(",");
		double coordinates[][] = new double[arry.length][2];
		for(int i = 0; i<arry.length; i++){
			String latlon[] = arry[i].split(" ");
			coordinates[i][0] = Double.parseDouble(latlon[0]);
			coordinates[i][1] = Double.parseDouble(latlon[1]);
		}
		return coordinates;
	}
	
	public static String geomFromArray(double[][] coordinates){
		String s = "MULTILINESTRING((";
		for(int i = 0; i<coordinates.length; i++){
			s += String.valueOf(coordinates[i][0]) + " " + String.valueOf(coordinates[i][1]);
			if(i!=coordinates.length-1)
				s += ",";
		}
		s += "))";
		return s;
	}
	
	public static double[][] reverse(double[][] arr){
		double t[][] = new double[arr.length][];
		for(int i = 0; i<arr.length; i++){
			t[i] = arr[arr.length-i-1];
		}
		return t;
	}
	
	public static double[] getPointFromGeom(String pointString){
		//String string = "POINT(-102.392936706543 30.1442527770996)";
		String sub = pointString.substring(6, pointString.length()-1);
		String[] split = sub.split(" ");
		double[] point = new double[2];
		point[0] = Double.parseDouble(split[0]);
		point[1] = Double.parseDouble(split[1]);
		return point;
	}
}
