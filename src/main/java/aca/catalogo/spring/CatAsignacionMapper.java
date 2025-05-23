package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatAsignacionMapper implements RowMapper<CatAsignacion> {
	public CatAsignacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatAsignacion asignacion = new CatAsignacion();
		
		asignacion.setAsignacionId(rs.getString("ASIGNACION_ID"));
		asignacion.setAsignacionNombre(rs.getString("ASIGNACION_NOMBRE"));
		asignacion.setDireccion(rs.getString("DIRECCION"));
		asignacion.setTelefono(rs.getString("TELEFONO"));
		
		return asignacion;
	}
}
