package pe.com.condominio.controlingresos.modules.ingreso.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingresos")
public class IngresoController {

	@GetMapping
	public String listar() {
		return "listar ingresos.";
	}
	
	@PostMapping
	public String create() {
		return "crear ingreso";
	}
	@PutMapping
	public String update() {
		return "actualizar ingreso.";
	}

}
