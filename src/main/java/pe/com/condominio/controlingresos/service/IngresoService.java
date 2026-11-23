package pe.com.condominio.controlingresos.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pe.com.condominio.controlingresos.dto.request.IngresoRequest;
import pe.com.condominio.controlingresos.dto.response.PageResponse;
import pe.com.condominio.controlingresos.model.Ingreso;
import pe.com.condominio.controlingresos.repository.IngresoRepository;

@Service
public class IngresoService {
	private final IngresoRepository ingresoRepository;
	public IngresoService(IngresoRepository ingresoRepository) {
		this.ingresoRepository = ingresoRepository;
	}
	public Long registrar(IngresoRequest request,Long createdBy) {
		if(!request.tipoIngreso().equals("PEATONAL") && !request.tipoIngreso().equals("VEHICULAR")) {
			throw new IllegalArgumentException("Tipo de ingreso no válido.");
		}
		if(request.tipoIngreso().equals("VEHICULAR") && (request.placa() == null || request.placa().isBlank())) {
			throw new IllegalArgumentException("La placa es obligatoria.");
		}
		Ingreso ingreso = new Ingreso(
				null,
				request.tipoIngreso(),
				request.tipoDocumento(),
				request.numeroDocumento(),
				request.nombres(),
				request.apellidos(),
				request.placa(),
				request.motivo(),
				request.direccionDestino(),
				request.observaciones(),
				OffsetDateTime.now(),
				null,
				createdBy,
				null,
				null,
				null
				);	
		return ingresoRepository.save(ingreso);
	}
	
    public PageResponse<Map<String, Object>> listar(int page,int limit){
    	if(page < 1) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
    				"La pagina debe ser mayor  a cero.");
    	}
    	if(limit < 1 || limit > 100) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
    				"El limite debe estar entre 1 y 100.");
    	}
    	long offset = ((long) page - 1 )*limit;
    	List<Map<String, Object>> content = ingresoRepository.listar(limit, offset);
    	long totalElements = ingresoRepository.contar();
    	long totalPages = (totalElements + limit - 1) /limit;
    	
    	return new PageResponse<>(
    			content,
    			page,
    			limit,
    			totalElements,
    			totalPages,
    			page >= totalPages
    			);
    }
}
