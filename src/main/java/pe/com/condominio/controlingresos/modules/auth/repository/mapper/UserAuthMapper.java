package pe.com.condominio.controlingresos.modules.auth.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pe.com.condominio.controlingresos.modules.auth.repository.model.UserAuthModel;

@Component
public class UserAuthMapper implements RowMapper<UserAuthModel>{

	@Override
	public UserAuthModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new UserAuthModel(
				rs.getLong("id"),
				rs.getString("nombres"),
				rs.getString("apellidos"),
				rs.getString("username"),
				rs.getString("password_hash"),
				rs.getBoolean("locked"),
				rs.getBoolean("disabled")
				);
	}
	
}
