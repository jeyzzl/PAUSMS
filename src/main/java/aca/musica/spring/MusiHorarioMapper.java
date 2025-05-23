package aca.musica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MusiHorarioMapper implements RowMapper<MusiHorario> {

	public MusiHorario mapRow(ResultSet rs, int rowNum) throws SQLException {
		MusiHorario objeto = new MusiHorario();
		
		objeto.setMaestroId(rs.getString("MAESTRO_ID"));
		objeto.setHoraInicio(rs.getString("HORA_INICIO"));
		objeto.setHoraFinal(rs.getString("HORA_FINAL"));
		objeto.setCupo(rs.getString("CUPO"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setDia(rs.getString("DIA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setMinInicio(rs.getString("MIN_INICIO"));
		objeto.setMinFinal(rs.getString("MIN_FINAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		
		return objeto;
	}

}
