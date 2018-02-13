<%@page import="postgreSQLJDBC.JunctionPoints"%>
<%@page import="postgreSQLJDBC.DirectionFinder"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="postgreSQLJDBC.RouteData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="postgreSQLJDBC.PostgreSQLConnection"%>
<%@page import="postgreSQLJDBC.RouteFinderClass" %>
<%
double x1=0,x2=0,y1=0,y2=0;
PostgreSQLConnection.setupConnection();
String opType = request.getParameter("opType");
//System.out.println(opType);
if(opType.compareTo("1")==0){
	String s = request.getParameter("source");
	String[] sp = s.split(",");
	String d = request.getParameter("destination");
	String[] dp = d.split(",");
	x1 = Double.parseDouble(sp[0]);
	y1 = Double.parseDouble(sp[1]);
	x2 = Double.parseDouble(dp[0]);
	y2 = Double.parseDouble(dp[1]);
}
else if(opType.compareTo("2")==0){
	String s = request.getParameter("source");
	String d = request.getParameter("destination");
	ResultSet rs = PostgreSQLConnection.getPlaceCoordinates(s);
	rs.next();
	x1 = rs.getDouble(1);
	y1 = rs.getDouble(2);
	rs = PostgreSQLConnection.getPlaceCoordinates(d);
	rs.next();
	x2 = rs.getDouble(1);
	y2 = rs.getDouble(2);
}





RouteFinderClass rc1 = new RouteFinderClass();
rc1.setPosition(x1,y1,x2,y2);
ArrayList<RouteData> routeDatas = rc1.run(1, 1);

int pointCounts = 0;
for(int k = 0; k<routeDatas.size(); k++){
	double coords[][] = routeDatas.get(k).getGeomAsArray();
	pointCounts += coords.length*2;
}
//System.out.println(pointCounts);
out.print(pointCounts);

for(int k = 0; k<routeDatas.size(); k++){
	double coords[][] = routeDatas.get(k).getGeomAsArray();
	for(int i = 0; i<coords.length; i++){
		for(int j = 0; j<2; j++){
			//if(i!=0 || j!=0 || k!=0){
				//out.print(" ");
			//}
			out.print(" " + coords[i][j]);
		}
	}
}

DirectionFinder dFinder = new DirectionFinder();
ArrayList<JunctionPoints> jPoints = dFinder.findDirection(routeDatas);
for(int i = 0; i<jPoints.size(); i++){
	out.print(" " + jPoints.get(i).getX() 
			+ " " + jPoints.get(i).getY() 
			+ " " + jPoints.get(i).getDirection());
}

%>