package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumPatrocinadorMapper implements RowMapper<AlumPatrocinador>{
	@Override
	public AlumPatrocinador mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumPatrocinador objeto = new AlumPatrocinador();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPatrocinadorId(rs.getString("PATROCINADOR_ID"));
		objeto.setPorcentaje(rs.getString("PORCENTAJE"));
		objeto.setCantidad(rs.getString("CANTIDAD"));
		
		return objeto;
	}
}