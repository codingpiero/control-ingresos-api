package pe.com.condominio.controlingresos.exception;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityExceptionHandler implements AuthenticationEntryPoint,AccessDeniedHandler {
	@Override
	// Usuario no autorizado
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		String safePath = request.getRequestURI() != null
                ? request.getRequestURI().replace("\"", "\\\"")
                : "";

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String json = """
                {
                  "status": 403,
                  "error": "Forbidden",
                  "message": "No tiene permisos para realizar esta operacion",
                  "path": "%s"
                }
                """.formatted(safePath);

        response.getWriter().write(json);
	}

	@Override
	// Usuario no autenticado
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		String safePath = request.getRequestURI() != null
                ? request.getRequestURI().replace("\"", "\\\"")
                : "";

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String json = """
                {
                  "status": 401,
                  "error": "Unauthorized",
                  "message": "No esta autenticado",
                  "path": "%s"
                }
                """.formatted(safePath);

        response.getWriter().write(json);
	}
	

}
