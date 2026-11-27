package pe.com.condominio.controlingresos.modules.visita.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/visitas")
public class VisitaController {
	@GetMapping
	public String listar() {
		return "listar visitas.";
	}
}
