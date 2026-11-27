package pe.com.condominio.controlingresos.modules.auth.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.condominio.controlingresos.modules.auth.dto.request.LoginRequest;
import pe.com.condominio.controlingresos.modules.auth.dto.response.LoginResponse;
import pe.com.condominio.controlingresos.modules.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {
	private AuthService authService;
	
	public AuthRestController(AuthService authService) {
		super();
		this.authService = authService;
	}


	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		String token = authService.login(request);
		
		return new LoginResponse(true,"login correcto",token);
	}
}
