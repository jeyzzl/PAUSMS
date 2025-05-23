package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaCorreoMapper implements RowMapper<ExaCorreo>{
	
	public ExaCorreo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaCorreo objeto = new ExaCorreo();
		
		objeto.setCorreoId(rs.getString("CORREO_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setFechaAct(rs.getString("FECHAACTUALIZACION"));		
		objeto.setEliminado(rs.getString("ELIMINADO"));
		objeto.setIdCorreo(rs.getString("IDCORREO"));
		
		return objeto;
	}
}