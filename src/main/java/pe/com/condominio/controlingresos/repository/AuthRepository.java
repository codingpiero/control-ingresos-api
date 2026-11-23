package pe.com.condominio.controlingresos.repository;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.com.condominio.controlingresos.dto.AuthUser;

@Repository
public class AuthRepository {
	private final JdbcTemplate jdbcTemplate;
	public AuthRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public Optional<AuthUser> findByUsername(String username){
		 String sql = """
		            SELECT
		                u.id,
		                c.username,
		                u.nombres,
		                u.apellidos,
		                c.password_hash,
		                ARRAY_AGG(DISTINCT r.code) AS roles
		            FROM control.users u
		            INNER JOIN control.auth_credentials c
		                ON c.user_id = u.id
		            INNER JOIN control.user_roles ur
		                ON ur.user_id = u.id
		            INNER JOIN control.roles r
		                ON r.id = ur.role_id
		            WHERE LOWER(c.username) = LOWER(?)
		              AND u.active = TRUE
		            GROUP BY
		                u.id,
		                c.username,
		                c.password_hash
		            """;

		        List<AuthUser> users = jdbcTemplate.query(sql, (rs, rowNum) -> {

		            Array sqlArray = rs.getArray("roles");

		            List<String> roles;
		            try {
		            	roles = Arrays.asList((String[])sqlArray.getArray());
		            }finally {
		            	sqlArray.free();
		            }

		            return new AuthUser(
		                rs.getLong("id"),
		                rs.getString("username"),
		                rs.getString("password_hash"),
		                rs.getString("nombres"),
		                rs.getString("apellidos"),
		                roles
		            );

		        }, username);

		        return users.stream().findFirst();

	}
}
