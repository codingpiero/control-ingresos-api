package pe.com.condominio.controlingresos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.condominio.controlingresos.dto.request.AuthRequest;
import pe.com.condominio.controlingresos.service.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request){
		String response = authService.authenticate(request);
		return ResponseEntity.ok(response);
	}
	
	
}
