package pe.com.condominio.controlingresos.repository;


import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.com.condominio.controlingresos.model.Ingreso;

@Repository
public class IngresoRepository {
	private final JdbcTemplate jdbcTemplate;
	
	public IngresoRepository (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Long save(Ingreso ingreso) {
		String sql = """
				INSERT INTO control.ingresos
				(
				tipo_ingreso,
				tipo_documento,
				numero_documento,
				nombres,
				apellidos,
				placa,
				motivo,
				direccion_destino,
				observaciones,
				created_by
				)
				VALUES
				(?,?,?,?,?,?,?,?,?,?)
				RETURNING id
				""";
		return jdbcTemplate.queryForObject(sql,
				Long.class,
				ingreso.tipoIngreso(),
				ingreso.tipoDocumento(),
				ingreso.numeroDocumento(),
				ingreso.nombres(),
				ingreso.apellidos(),
				ingreso.placa(),
				ingreso.motivo(),
				ingreso.direccionDestino(),
				ingreso.observaciones(),
				ingreso.createdBy());
	}
	
	public List<Map<String, Object>> listar(int limit,long offset){
		String sql = """
				                SELECT
                    id,
                    tipo_ingreso,
                    tipo_documento,
                    numero_documento,
                    nombres,
                    apellidos,
                    placa,
                    motivo,
                    direccion_destino,
                    fecha_ingreso,
                    fecha_salida
                FROM control.ingresos
                WHERE deleted_at IS NULL
                ORDER BY fecha_ingreso DESC, id DESC
                LIMIT ? OFFSET ?
				""";
		return jdbcTemplate.queryForList(sql,limit,offset);
	}
	
	public long contar() {
		String sql = """
				SELECT COUNT(*)
                FROM control.ingresos
                WHERE deleted_at IS NULL
				""";
		Long total = jdbcTemplate.queryForObject(sql,Long.class);
		return total != null ? total :0L;
	}
	
	
	
}
