package pe.com.condominio.controlingresos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IngresoRequest(
		@NotBlank(message="El tipo de ingreso es obligatorio.")
		String tipoIngreso,
		@NotBlank(message="El tipo de documento es obligatorio.")
		String tipoDocumento,
		@NotBlank(message="El numero documento es obligatorio.")
		String numeroDocumento,
		@NotBlank(message="Los nombres son obligatorios.")
		@Size(max = 100)
		String nombres,
		@NotBlank(message="Los apellidos son obligatorios.")
		@Size(max = 100)
		String apellidos,
		@Size(max = 10)
		String placa,
		@NotBlank(message = "El motivo es obligatorio.")
		@Size(max = 200)
		String motivo,
		@NotBlank(message = "La direccion de destino es obligatorio.")
		@Size(max = 100)
		String direccionDestino,
		@Size(max = 20)
		String observaciones
		) 
{
}
