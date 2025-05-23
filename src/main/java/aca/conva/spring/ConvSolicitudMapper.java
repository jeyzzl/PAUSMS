package aca.conva.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConvSolicitudMapper implements RowMapper<ConvSolicitud> {
	public ConvSolicitud mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConvSolicitud solicitud = new ConvSolicitud();
		
		solicitud.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		solicitud.setCursoId(rs.getString("CURSO_ID"));
		solicitud.setUsuario(rs.getString("USUARIO"));
		solicitud.setFecha(rs.getString("FECHA"));
		solicitud.setTipo(rs.getString("TIPO"));
		solicitud.setProcesoId(rs.getString("PROCESO_ID"));
		solicitud.setCarrera(rs.getString("CARRERA"));
		solicitud.setNota(rs.getString("NOTA"));
		solicitud.setMateria_O(rs.getString("MATERIA_O"));
		solicitud.setCreditos_O(rs.getString("CREDITOS_O"));
		solicitud.setNota_O(rs.getString("NOTA_0"));
		return solicitud;
	}
}
