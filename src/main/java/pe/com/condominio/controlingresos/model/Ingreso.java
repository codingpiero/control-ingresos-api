package pe.com.condominio.controlingresos.model;

import java.time.OffsetDateTime;

public record Ingreso(
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
		OffsetDateTime fechaSalida,
		Long createdBy,
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt,
		OffsetDateTime deleteAt
		) 
{
}
