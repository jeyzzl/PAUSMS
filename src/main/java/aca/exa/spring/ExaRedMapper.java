package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaRedMapper implements RowMapper<ExaRed>{
	
	public ExaRed mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaRed objeto = new ExaRed();
		
		objeto.setRedSocialId(rs.getString("REDSOCIAL_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setRed(rs.getString("RED"));
		objeto.setFechaAct(rs.getString("FECHAACTUALIZACION"));		
		objeto.setEliminado(rs.getString("ELIMINADO"));
		objeto.setIdRedSocial(rs.getString("IDREDSOCIAL"));
		
		return objeto;
	}
}