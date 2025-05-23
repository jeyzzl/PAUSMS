package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaBloqueMapper implements RowMapper<CargaBloque>{
	
	public CargaBloque mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaBloque objeto = new CargaBloque();
		
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setNombreBloque(rs.getString("NOMBRE_BLOQUE"));
		objeto.setFInicio(rs.getString("F_INICIO"));
		objeto.setFFinal(rs.getString("F_FINAL"));
		objeto.setFCierre(rs.getString("F_CIERRE"));	
		objeto.setInicioClases(rs.getString("INICIO_CLASES"));
		
		return objeto;
	}
}