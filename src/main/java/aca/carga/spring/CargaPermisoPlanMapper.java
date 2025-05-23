package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaPermisoPlanMapper implements RowMapper<CargaPermisoPlan>{
	
	public CargaPermisoPlan mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaPermisoPlan objeto = new CargaPermisoPlan();
		
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setRecuperacion(rs.getString("RECUPERACION"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}
}