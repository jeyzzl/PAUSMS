package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaEmpleoMapper implements RowMapper<ExaEmpleo>{
	
	public ExaEmpleo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaEmpleo objeto = new ExaEmpleo();
		
		objeto.setEmpleoId(rs.getString("EMPLEO_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setEmpresa(rs.getString("EMPRESA"));
		objeto.setPeriodo(rs.getString("PERIODO"));
		objeto.setFechaAct(rs.getString("FECHAACTUALIZACION"));
		objeto.setEliminado(rs.getString("ELIMINADO"));
		objeto.setIdEmpleo(rs.getString("IDEMPLEO"));
		
		return objeto;
	}
}