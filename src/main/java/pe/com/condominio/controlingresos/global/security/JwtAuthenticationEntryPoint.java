package pe.com.condominio.controlingresos.global.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.com.condominio.controlingresos.global.http.HttpStatus.ErrorCode;
import pe.com.condominio.controlingresos.global.response.ApiErrorResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;
	
	public JwtAuthenticationEntryPoint (ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ErrorCode ec = "TOKE_EXPIRED".equals(request.getAttribute("jwt_error"))
				? ErrorCode.TOKEN_EXPIRED : ErrorCode.UNAUTHORIZED;
		String message = ec == ErrorCode.TOKEN_EXPIRED ? "El token ha expirado": "No autenticado o token inválido";
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		objectMapper.writeValue(response.getOutputStream(),
				ApiErrorResponse.of(401, ec.name(), message, request.getRequestURI()));
		
	}

}
