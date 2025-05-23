package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitRequisitoAlumnoMapper implements RowMapper<BitRequisitoAlumno> {
	
	@Override
	public BitRequisitoAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitRequisitoAlumno objeto = new BitRequisitoAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTramiteId(rs.getString("TRAMITE_ID"));
		objeto.setRequisitoId(rs.getString("REQUISITO_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCodigoEmpleado(rs.getString("CODIGO_EMPLEADO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFolio(rs.getString("FOLIO"));
		
		return objeto;
	}
}