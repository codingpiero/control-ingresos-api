package pe.com.condominio.controlingresos.modules.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import pe.com.condominio.controlingresos.global.utils.JwtUtil;
import pe.com.condominio.controlingresos.modules.auth.dto.request.LoginRequest;
import pe.com.condominio.controlingresos.modules.auth.repository.model.CustomUserDetailModel;

@Service
public class AuthService {
	private final JwtUtil jwtUtil;
	private AuthenticationManager authenticationManager;

	public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	public String login(LoginRequest request) {
		UsernamePasswordAuthenticationToken credentials = 
				new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
		
		Authentication authentication = authenticationManager.authenticate(credentials);
		CustomUserDetailModel user = (CustomUserDetailModel) authentication.getPrincipal();
		String token = jwtUtil.generarToken(user);
		return token;
	
	}
	 
}
