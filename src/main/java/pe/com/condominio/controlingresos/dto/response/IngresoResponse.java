package pe.com.condominio.controlingresos.dto.response;

import java.time.OffsetDateTime;

public record IngresoResponse(
		Long id,
		String tipoIngreso,
		String tipoDocumento,
		String numeroDocumento,
		String nombres,
		String apellidos,
		String placa,
		String motivo,
		String direccionDestino,
		String observaciones,
		OffsetDateTime fechaIngreso,
		OffsetDateTime  fechaSalida,
		Long createdBy
		) 
{
}
