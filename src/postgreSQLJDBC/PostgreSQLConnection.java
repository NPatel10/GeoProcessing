package postgreSQLJDBC;

import java.sql.*;

public class PostgreSQLConnection {
	static Connection connection = null;
	static PreparedStatement nearestEdgeStatement = null;
	static PreparedStatement nearestNodeStatement = null;
	static PreparedStatement routeFinderStatement = null;
	static PreparedStatement trafficEdgeIdStatement = null;
	static PreparedStatement searchStringStatement = null;
	static PreparedStatement updateTrafficStatement = null;
	static PreparedStatement PlaceCoordinatesStatement = null;
	static PreparedStatement getRoutesCountStatement = null;
	public PostgreSQLConnection() {
		try {
			Class.forName(SConstants.POSTGRES_DRIVER);
			connection = DriverManager.getConnection(SConstants.CONNECTION_STRING, SConstants.UNAME, SConstants.PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public static void setupConnection() {
		try {
			Class.forName(SConstants.POSTGRES_DRIVER);
			connection = DriverManager.getConnection(SConstants.CONNECTION_STRING, SConstants.UNAME, SConstants.PASSWORD);
			nearestEdgeStatement = connection.prepareStatement(SQLQuery.NEAREST_EDGE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			nearestNodeStatement = connection.prepareStatement(SQLQuery.NEAREST_NODE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			routeFinderStatement = connection.prepareStatement(SQLQuery.ROUTE_FINDER, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			trafficEdgeIdStatement = connection.prepareStatement(SQLQuery.TRAFFIC_EDGE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			searchStringStatement = connection.prepareStatement(SQLQuery.SEARCH_QUERY_RESULT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			updateTrafficStatement = connection.prepareStatement(SQLQuery.TRAFFIC_UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			PlaceCoordinatesStatement = connection.prepareStatement(SQLQuery.LAT_LON_FROM_PLACE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			getRoutesCountStatement = connection.prepareStatement(SQLQuery.ROUTE_COUNT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public static void closeConeection(){
		try {
			nearestEdgeStatement.close();
			nearestNodeStatement.close();
			routeFinderStatement.close();
			trafficEdgeIdStatement.close();
			searchStringStatement.close();
			updateTrafficStatement.close();
			PlaceCoordinatesStatement.close();
			getRoutesCountStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static ResultSet getNearestNodes(double x, double y){
		try {
			nearestNodeStatement.setDouble(1, x);
			nearestNodeStatement.setDouble(2, y);
			//System.out.println(nearestNodeStatement.toString());
			return nearestNodeStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ResultSet getNearestEdge(double x, double y){
		try {
			nearestEdgeStatement.setDouble(1, x);
			nearestEdgeStatement.setDouble(3, x);
			nearestEdgeStatement.setDouble(5, x);
			nearestEdgeStatement.setDouble(2, y);
			nearestEdgeStatement.setDouble(4, y);
			nearestEdgeStatement.setDouble(6, y);
			return nearestEdgeStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ResultSet getRoute(int source, int target){
		try {
			routeFinderStatement.setInt(1, source);
			routeFinderStatement.setInt(2, target);
			//System.out.println(routeFinderStatement.toString());
			return routeFinderStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ResultSet getTrafficEdgeId(double x, double y){
		try {
			trafficEdgeIdStatement.setDouble(1, x);
			trafficEdgeIdStatement.setDouble(2, y);
			//System.out.println(trafficEdgeIdStatement.toString());
			return trafficEdgeIdStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ResultSet getSearchQueryResult(String s){
		try {
			//System.out.println(searchStringStaement.toString());
			searchStringStatement.setString(1, "%" + s + "%");
			return searchStringStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ResultSet getPlaceCoordinates(String s){
		try {
			PlaceCoordinatesStatement.setString(1,s.toLowerCase());
			//System.out.println(PlaceCoordinatesStatement.toString());
			
			return PlaceCoordinatesStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static int updateTraffic(int id, int val){
		try {
			//System.out.println(searchStringStaement.toString());
			updateTrafficStatement.setInt(1, val);
			updateTrafficStatement.setInt(2, id);
			return updateTrafficStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	public static ResultSet getRouteCount(int source){
		try {
			//System.out.println(getRoutesCountStatement.toString());
			getRoutesCountStatement.setInt(1, source);
			getRoutesCountStatement.setInt(2, source);
			getRoutesCountStatement.setInt(3, source);
			return getRoutesCountStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
