package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaEstadoMapper implements RowMapper<ExaEstado>{
	
	public ExaEstado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaEstado objeto = new ExaEstado();
		
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setNombreEstado(rs.getString("ESTADO_NOMBRE"));
		
		return objeto;
	}
}