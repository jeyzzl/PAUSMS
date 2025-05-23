package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaHorarioMapper implements RowMapper<CargaHorario>{

	public CargaHorario mapRow(ResultSet rs, int arg1) throws SQLException {
		CargaHorario objeto = new CargaHorario();

		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setSalonId(rs.getString("SALON_ID"));
		objeto.setDia(rs.getString("DIA"));
		objeto.setHoraInicio(rs.getString("HORA_INICIO"));
		objeto.setMinutoInicio(rs.getString("MINUTO_INICIO"));
		objeto.setHoraFinal(rs.getString("HORA_FINAL"));
		objeto.setMinutoFinal(rs.getString("MINUTO_FINAL"));
		objeto.setHorarioId(rs.getString("HORARIO_ID"));
		objeto.setPeriodo(rs.getString("PERIODO"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		
		return objeto;
	}

}