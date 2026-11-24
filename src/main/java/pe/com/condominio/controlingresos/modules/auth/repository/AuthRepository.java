package pe.com.condominio.controlingresos.modules.auth.repository;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.com.condominio.controlingresos.modules.auth.model.AuthUser;

@Repository
public class AuthRepository {
	private final JdbcTemplate jdbcTemplate;
	public AuthRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public AuthUser findByUsername(String username) {
		String sql = """
				SELECT
                    u.id,
                    c.username,
                    c.password_hash,
                    u.active,
                    r.code AS role_code
                FROM control.users u
                INNER JOIN control.auth_credentials c
                    ON c.user_id = u.id
                LEFT JOIN control.user_roles ur
                    ON ur.user_id = u.id
                LEFT JOIN control.roles r
                    ON r.id = ur.role_id
                WHERE LOWER(c.username) = LOWER(?)
				""";
        return jdbcTemplate.query(
                sql,
                rs -> {

                    if (!rs.next()) {
                        return null;
                    }

                    Long id = rs.getLong("id");
                    String dbUsername = rs.getString("username");
                    String passwordHash = rs.getString("password_hash");
                    boolean active = rs.getBoolean("active");

                    var roles = new LinkedHashSet<String>();

                    do {
                        String role = rs.getString("role_code");

                        if (role != null) {
                            roles.add(role);
                        }

                    } while (rs.next());

                    return new AuthUser(
                            id,
                            dbUsername,
                            passwordHash,
                            active,
                            new ArrayList<>(roles)
                    );
                },
                username
        );
	}
}
