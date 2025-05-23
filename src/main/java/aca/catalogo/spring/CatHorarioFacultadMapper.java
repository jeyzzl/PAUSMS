package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatHorarioFacultadMapper implements RowMapper<CatHorarioFacultad> {
	public CatHorarioFacultad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatHorarioFacultad objeto = new CatHorarioFacultad();
		
		objeto.setHorarioId(rs.getString("HORARIO_ID"));
		objeto.setPeriodo(rs.getString("PERIODO"));
		objeto.setTurno(rs.getString("TURNO"));
		objeto.setHoraInicio(rs.getString("HORA_INICIO"));
		objeto.setHoraFinal(rs.getString("HORA_FINAL"));
		objeto.setMinutoInicio(rs.getString("MINUTO_INICIO"));
		objeto.setMinutoFinal(rs.getString("MINUTO_FINAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}
}
