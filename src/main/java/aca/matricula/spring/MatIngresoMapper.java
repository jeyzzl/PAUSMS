package aca.matricula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MatIngresoMapper implements RowMapper<MatIngreso>{
	
	public MatIngreso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MatIngreso objeto = new MatIngreso();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setPropios(rs.getString("PROPIOS"));
		objeto.setBecario(rs.getString("BECARIO"));
		objeto.setColportaje(rs.getString("COLPORTAJE"));
		objeto.setOtro(rs.getString("OTRO"));
	
		return objeto;
	}
}