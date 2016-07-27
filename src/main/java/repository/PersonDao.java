package repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import domain.Person;

public class PersonDao {
	public Person getPersonByLogin(String login) {
		JdbcTemplate jdbcTemplate = SqliteJdbcTemplate.getJdbcTemplate();
		
		try {
			Person person = jdbcTemplate.queryForObject("select login, password from person where login = ?", new Object[]{login}, new RowMapper<Person>() {
				
				public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Person.Builder()
					.login(rs.getString("login"))
					.password(rs.getString("password"))
					.build();
				}
				
			});
			return person;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
