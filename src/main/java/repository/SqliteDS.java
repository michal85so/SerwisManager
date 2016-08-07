package repository;


import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SqliteDS {
	private DriverManagerDataSource dmds;

	private SqliteDS() {
		dmds = new DriverManagerDataSource();
		dmds.setDriverClassName("org.sqlite.JDBC");
		dmds.setUrl("jdbc:sqlite:sm.db");
	}
	
	private static class Handler {
		static SqliteDS instance = new SqliteDS();
	}
	
	public static SqliteDS getInstance() {
		return Handler.instance;
	}
	
	public DriverManagerDataSource getDataSource() {
		return dmds;
	}
}
