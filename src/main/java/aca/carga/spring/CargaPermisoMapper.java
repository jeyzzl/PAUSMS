package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaPermisoMapper implements RowMapper<CargaPermiso>{
	
	public CargaPermiso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaPermiso objeto = new CargaPermiso();
		
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setRecuperacion(rs.getString("RECUPERACION"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}
}