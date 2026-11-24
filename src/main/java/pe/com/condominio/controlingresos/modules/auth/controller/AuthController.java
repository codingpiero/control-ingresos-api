package pe.com.condominio.controlingresos.modules.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pe.com.condominio.controlingresos.modules.auth.dto.request.LoginRequest;
import pe.com.condominio.controlingresos.modules.auth.dto.response.LoginResponse;
import pe.com.condominio.controlingresos.modules.auth.model.AuthUser;
import pe.com.condominio.controlingresos.modules.auth.service.AuthService;
import pe.com.condominio.controlingresos.modules.auth.service.JwtService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final AuthService authService;
	private final JwtService jwtService;
	public AuthController(AuthService authService,JwtService jwtService) {
		this.authService = authService;
		this.jwtService = jwtService;
	}
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
		AuthUser user = authService.authenticate(request);
		String accessToken = jwtService.generateAccessToken(user);
		
		LoginResponse response = new LoginResponse(
				true,
				"Token generado correctamente",
				accessToken,
				"Bearer",
				jwtService.getExpirationSeconds()
				);
		return ResponseEntity.ok(response);
	}
}
