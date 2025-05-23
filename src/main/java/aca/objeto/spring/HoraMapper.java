package aca.objeto.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HoraMapper implements RowMapper<Hora> {

	public Hora mapRow(ResultSet rs, int rowNum) throws SQLException {
		Hora objeto = new Hora();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setSalon(rs.getString("SALON_ID"));
		objeto.setDia(rs.getString("DIA"));
		objeto.setHorarioId(rs.getString("HORARIO_ID"));
		objeto.setPeriodo(rs.getString("PERIODO"));
		objeto.setInicio(rs.getString("INICIO"));
		objeto.setFin(rs.getString("FIN"));
		
		return objeto;
	}
	
	
}
