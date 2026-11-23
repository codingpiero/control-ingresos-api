package pe.com.condominio.controlingresos.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.com.condominio.controlingresos.dto.AuthUser;
import pe.com.condominio.controlingresos.dto.request.AuthRequest;
import pe.com.condominio.controlingresos.global.security.JwtService;
import pe.com.condominio.controlingresos.repository.AuthRepository;

@Service
public class AuthService {
	private final AuthRepository authRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	public AuthService(AuthRepository authRepository,PasswordEncoder passwordEncoder,JwtService jwtService) {
		this.authRepository = authRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	public String authenticate(AuthRequest request) {
		AuthUser user = authRepository
				.findByUsername(request.username())
				.orElseThrow(()->
				  new RuntimeException("Credenciales Incorrectas.")
						);
		boolean validPassword = passwordEncoder.matches(request.password(),user.passwordHash());
		
		if(!validPassword) {
			throw new RuntimeException("Credenciales Incorrectas.");
		}
		String token = jwtService.generateToken(user);
		
		return token;
	}
}
