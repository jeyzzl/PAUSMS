package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalAlumnoMapper implements RowMapper<SalAlumno>{
	
	public SalAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SalAlumno objeto = new SalAlumno();
		
		objeto.setSalidaId(rs.getString("SALIDA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
				
		return objeto;
	}
}