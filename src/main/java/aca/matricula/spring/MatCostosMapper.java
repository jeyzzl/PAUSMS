package aca.matricula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MatCostosMapper implements RowMapper<MatCostos>{
	
	public MatCostos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MatCostos objeto = new MatCostos();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		
		return objeto;
	}
}