<%@page import="java.sql.ResultSet"%>
<%@page import="postgreSQLJDBC.PostgreSQLConnection"%>
<% 
	String searchString = request.getParameter("s");
	PostgreSQLConnection.setupConnection();
	try{
		ResultSet rs = PostgreSQLConnection.getSearchQueryResult(searchString);
		int t = 0;
		while(rs.next()){
			if(t!=0){
				out.print("*");
				//System.out.print("*");
			}
				
			out.print(rs.getString(1));
			//System.out.print(rs.getString(1));
			t++;
		} 
		
	}catch(Exception e){
		e.printStackTrace();
	}
%>