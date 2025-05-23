package aca.cita.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CitaReservadaMapper implements RowMapper<CitaReservada>{
	
	public CitaReservada mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CitaReservada objeto = new CitaReservada();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setDia(rs.getString("DIA"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));		
		
		return objeto;
	}
}