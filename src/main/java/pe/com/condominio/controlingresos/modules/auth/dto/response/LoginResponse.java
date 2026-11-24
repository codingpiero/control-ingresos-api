package pe.com.condominio.controlingresos.modules.auth.dto.response;


public record LoginResponse(
		boolean sucess,
		String message,
		String accessToken,
		String tokenType,
		long expiresIn
		) 
{

}
