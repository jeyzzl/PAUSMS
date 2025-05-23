package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmReservadaMapper implements RowMapper<AdmReservada>{
	
	public AdmReservada mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AdmReservada objeto = new AdmReservada();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setDia(rs.getString("DIA"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		
		return objeto;
	}
}