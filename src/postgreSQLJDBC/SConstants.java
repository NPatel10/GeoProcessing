package postgreSQLJDBC;

public interface SConstants {
	final String POSTGRES_DRIVER = "org.postgresql.Driver";
	final String HOST = "localhost";
	final String PORT = "8081";
	final String DB_NAME = "Maps";
	final String CONNECTION_STRING = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
	final String UNAME = "postgres";
	final String PASSWORD = "geoserver";
	final String ROUTE_TABLE = "osm_roads";
	final String VERTICES_TABLE = "osm_roads_vertices_pgr";
	final String PLACENAME_TABLE = "county_point_texas_gcs_sp2";
	
	// for texas
//	final String ROUTE_TABLE = "texas_roads_gcs";
//	final String VERTICES_TABLE = "texas_roads_gcs_vertices_pgr";
}
