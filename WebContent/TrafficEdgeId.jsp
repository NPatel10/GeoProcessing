


<%@page import="postgreSQLJDBC.BasicMath"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="postgreSQLJDBC.PostgreSQLConnection"%>
<% 
	PostgreSQLConnection.setupConnection();

	int opType = Integer.parseInt(request.getParameter("opType"));
	if(opType == 0){
		double x = Double.parseDouble(request.getParameter("x"));
		double y = Double.parseDouble(request.getParameter("y"));
		try{
			ResultSet rs = PostgreSQLConnection.getTrafficEdgeId(x, y);
			rs.next();
			out.print(rs.getString(2)+ "*");
			double coords[][] = BasicMath.geomAsArray(rs.getString(1));
			
			for(int i = 0; i<coords.length; i++){
				for(int j = 0; j<2; j++){
					if(i!=0 || j!=0){
						out.print(" ");
					}
					out.print(coords[i][j]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	else if(opType == 1){
		int id = Integer.parseInt(request.getParameter("id"));
		int val = Integer.parseInt(request.getParameter("val"));
		
		int i = PostgreSQLConnection.updateTraffic(id, val);
		out.print(i);
	}
	
%>