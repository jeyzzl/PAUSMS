package aca.matricula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MatEventoMapper implements RowMapper<MatEvento>{
	
	public MatEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MatEvento objeto = new MatEvento();
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setEventoNombre(rs.getString("EVENTO_NOMBRE"));
		objeto.setEstado(rs.getString("ESTADO"));
	
		return objeto;
	}
}