package repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SqliteJdbcTemplate {
	private static JdbcTemplate jdbcTemplate;
	
	static {
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		dmds.setDriverClassName("org.sqlite.JDBC");
		dmds.setUrl("jdbc:sqlite:sm.db");
		jdbcTemplate = new JdbcTemplate(dmds);
	}
	
	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
