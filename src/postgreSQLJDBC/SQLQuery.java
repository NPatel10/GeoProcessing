package postgreSQLJDBC;

public interface SQLQuery {
	final String NEAREST_NODE = "SELECT id FROM " + SConstants.VERTICES_TABLE + " ORDER BY the_geom <-> "
				+ "ST_SetSRID(ST_Point(?, ?),0) LIMIT 10";
//	final String NEAREST_EDGE = "SELECT gid, source, target, name, length, traffic, st_astext(geom) AS geom,"
//			+ " st_x(st_closestpoint(geom, st_point(" + x + ", " + y + "))) AS xNearby, "
//					+ "st_y(st_closestpoint(geom, st_point(" + x + ", " + y + "))) AS yNearby, "
//							+ "st_distance(geom, st_point(" + x + ", " + y + "), false) AS dis FROM " 
//				+ SConstants.ROUTE_TABLE + " ORDER BY dis LIMIT 1";
	final String NEAREST_EDGE = "SELECT gid, source, target, length, traffic, st_astext(geom) AS geom,"
			+ " st_x(st_closestpoint(geom, st_point( ?, ?))) AS xNearby, "
					+ "st_y(st_closestpoint(geom, st_point(?, ?))) AS yNearby, "
							+ "st_distance(geom, st_point(?, ?), false) AS dis FROM " 
				+ SConstants.ROUTE_TABLE + " ORDER BY dis LIMIT 1";
	final String ROUTE_FINDER = "SELECT seq, source, target, traffic, length, gid, st_astext(geom) AS geom " 
				+ " FROM pgr_dijkstra3_0('" + SConstants.ROUTE_TABLE +	"', ?, ?)";
	
	final String TRAFFIC_EDGE = "SELECT st_astext(geom), gid, " +
			"ST_Distance(geom, ST_POINT(?, ?), false) AS dist" + 
			" FROM " + SConstants.ROUTE_TABLE + " ORDER BY dist LIMIT 1";
	
	// lat = y, lon = x
	final String SEARCH_QUERY_RESULT = "SELECT name FROM " + SConstants.PLACENAME_TABLE + " WHERE " +
			"lower(name) LIKE ? ORDER BY name LIMIT 5";
	final String LAT_LON_FROM_PLACE = "SELECT lon, lat FROM " + SConstants.PLACENAME_TABLE + " WHERE lower(name)=?";
	 
	final String TRAFFIC_UPDATE = "UPDATE " + SConstants.ROUTE_TABLE + " SET traffic=? where gid=?";
	final String ROUTE_COUNT = "SELECT gid, CASE WHEN source=? THEN st_astext(ST_PointN(st_geometryn(geom,1),2)) " +
				"ELSE st_astext(ST_PointN(st_geometryn(st_reverse(geom),1),2)) END AS point " +
				"FROM " + SConstants.ROUTE_TABLE + " where source=? or target=?";
}
