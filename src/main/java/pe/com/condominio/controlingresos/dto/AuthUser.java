package pe.com.condominio.controlingresos.dto;

import java.util.List;

public record AuthUser(
		Long id,
		String username,
		String passwordHash,
		String nombres,
		String apellidos,
		List<String> roles) 
{

}
