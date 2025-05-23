package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaTelefonoMapper implements RowMapper<ExaTelefono>{
	
	public ExaTelefono mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaTelefono objeto = new ExaTelefono();
		
		objeto.setTelefonoId(rs.getString("TELEFONO_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setFechaAct(rs.getString("FECHAACTUALIZACION"));		
		objeto.setEliminado(rs.getString("ELIMINADO"));
		objeto.setIdTelefono(rs.getString("IDTELEFONO"));
		
		return objeto;
	}
}