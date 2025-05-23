package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoHorarioMapper implements RowMapper<CargaGrupoHorario>{
	
	public CargaGrupoHorario mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoHorario objeto = new CargaGrupoHorario();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setSalonId(rs.getString("SALON_ID"));
		objeto.setHorario(rs.getString("HORARIO"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setValidaCruce(rs.getString("VALIDA_CRUCE"));
	
		return objeto;
	}
}