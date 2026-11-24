package pe.com.condominio.controlingresos.modules.ingreso.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingresos")
public class IngresoController {

	@GetMapping
	@PreAuthorize("hasAnyRole('ASOCIADO','VIGILANTE')")
	public String listar() {
		return "listar ingresos.";
	}
	@PostMapping
	@PreAuthorize("hasRole('VIGILANTE')")
	public String create() {
		return "ingreso creado.";
	}

}
