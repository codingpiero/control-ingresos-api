package pe.com.condominio.controlingresos.modules.auth.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.com.condominio.controlingresos.modules.auth.repository.mapper.UserAuthMapper;
import pe.com.condominio.controlingresos.modules.auth.repository.model.UserAuthModel;

@Repository
public class AuthRepository {
	private JdbcTemplate jdbcTemplate;
	private UserAuthMapper userAuthMapper;

	public AuthRepository(JdbcTemplate jdbcTemplate, UserAuthMapper userAuthMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.userAuthMapper = userAuthMapper;
	}

	public Optional<UserAuthModel> buscarUsuario(String username) {
		String sql = """
								SELECT
				u.id as id,
				u.nombres as nombres,
				u.apellidos as apellidos,
				ac.username as username,
				ac.password_hash as password_hash,
				ac.disabled as disabled,
				ac.locked as locked
				FROM
				control.users u
				INNER JOIN
				control.auth_credentials ac
				ON
				u.id = ac.user_id
				WHERE
				ac.username = ?
												""";

		List<UserAuthModel> user = jdbcTemplate.query(sql, userAuthMapper, username);
		return user.stream().findFirst();
	}

	public List<String> obtenerRoles(long id) {
		String sql = """
								SELECT
				r.name as rol
				FROM
				control.roles r
				INNER JOIN
				control.user_roles ur
				ON
				r.id = ur.role_id
				WHERE
				ur.user_id = ?
								""";
		List<String> roles = jdbcTemplate.queryForList(sql,String.class,id);
		return roles;
	}
}
