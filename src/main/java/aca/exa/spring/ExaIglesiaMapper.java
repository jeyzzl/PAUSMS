package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaIglesiaMapper implements RowMapper<ExaIglesia>{
	
	public 	ExaIglesia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaIglesia objeto = new ExaIglesia();
		
		objeto.setIglesiaId(rs.getString("IGLESIA_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setIglesia(rs.getString("IGLESIA"));
		objeto.setFuncion(rs.getString("FUNCION"));
		objeto.setFechaAct(rs.getString("FECHAACTUALIZACION"));
		objeto.setEliminado(rs.getString("ELIMINADO"));
		objeto.setIdEgresadoIglesia(rs.getString("IDEGRESADOIGLESIA"));
		return objeto;
	}
}

