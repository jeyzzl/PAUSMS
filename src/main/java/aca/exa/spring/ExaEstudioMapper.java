package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaEstudioMapper implements RowMapper<ExaEstudio>{
	
	public ExaEstudio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaEstudio objeto = new ExaEstudio();
		
		objeto.setEstudioId(rs.getString("ESTUDIO_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setPeriodo(rs.getString("PERIODO"));
		objeto.setFechaAct(rs.getString("FECHAACTUALIZACION"));
		objeto.setEliminado(rs.getString("ELIMINADO"));
		objeto.setIdEstudio(rs.getString("IDESTUDIO"));
		
		return objeto;
	}
}