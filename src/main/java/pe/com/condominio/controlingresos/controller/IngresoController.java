package pe.com.condominio.controlingresos.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pe.com.condominio.controlingresos.dto.request.IngresoRequest;
import pe.com.condominio.controlingresos.dto.response.PageResponse;
import pe.com.condominio.controlingresos.service.IngresoService;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {
	private final IngresoService ingresoService;
	public IngresoController(IngresoService ingresoService) {
		this.ingresoService = ingresoService;
	}
	@PostMapping
	public ResponseEntity<Map<String, Object>> registrar(@Valid @RequestBody IngresoRequest request){
		Long createdBy = 1L;
		Long id = ingresoService.registrar(request, createdBy);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(Map.of(
						"message","Ingreso registrado correctament",
						"id",id));
	}

	@GetMapping
	public ResponseEntity<PageResponse<Map<String, Object>>> listar(
			@RequestParam(name="page",defaultValue = "1") int page,
			@RequestParam(name="limit",defaultValue = "10") int limit
			){
		PageResponse<Map<String, Object>> response = ingresoService.listar(page, limit);
		
		return ResponseEntity.ok(response);
	}
}
