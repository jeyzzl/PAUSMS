package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmPeriodoMapper implements RowMapper<AdmPeriodo>{
	@Override
	public AdmPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AdmPeriodo objeto = new AdmPeriodo();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setDia(rs.getString("DIA"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setHoraInicio(rs.getString("HORA_INICIO"));
		objeto.setMinInicio(rs.getString("MIN_INICIO"));
		objeto.setHoraFin(rs.getString("HORA_FIN"));
		objeto.setMinFin(rs.getString("MIN_FIN"));
		objeto.setCupo(rs.getString("CUPO"));
		
		return objeto;
	}
}