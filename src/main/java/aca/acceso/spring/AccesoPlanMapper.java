package aca.acceso.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccesoPlanMapper implements RowMapper<AccesoPlan>{
	@Override
	public AccesoPlan mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AccesoPlan objeto = new AccesoPlan();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}
}