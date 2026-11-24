package pe.com.condominio.controlingresos.modules.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import pe.com.condominio.controlingresos.modules.auth.dto.request.LoginRequest;
import pe.com.condominio.controlingresos.modules.auth.model.AuthUser;

@Service
public class AuthService {
	private final AuthenticationManager authenticactionManager;
	public AuthService(AuthenticationManager authenticationManager) {
		this.authenticactionManager = authenticationManager;
	}
	public AuthUser authenticate(LoginRequest request) {
		Authentication authentication = authenticactionManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(request.username(), request.password()));
		return (AuthUser) authentication.getPrincipal();
	}
}
