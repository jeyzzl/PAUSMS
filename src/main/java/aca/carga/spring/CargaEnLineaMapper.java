package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaEnLineaMapper implements RowMapper<CargaEnLinea>{
	
	public CargaEnLinea mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaEnLinea objeto = new CargaEnLinea();
	
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setfInicio(rs.getString("F_INICIO"));
		objeto.setfFinal(rs.getString("F_FINAL"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setCarta(rs.getString("CARTA"));
		
		return objeto;
	}

}
