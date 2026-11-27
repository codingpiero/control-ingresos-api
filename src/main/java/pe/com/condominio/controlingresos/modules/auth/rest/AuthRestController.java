package pe.com.condominio.controlingresos.modules.auth.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.condominio.controlingresos.global.response.ApiResponse;
import pe.com.condominio.controlingresos.modules.auth.dto.request.LoginRequest;
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
	public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
		String token = authService.login(request);
		return ResponseEntity.ok(ApiResponse.ok(token));
	}
}
