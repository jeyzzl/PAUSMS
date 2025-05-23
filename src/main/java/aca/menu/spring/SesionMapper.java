package aca.menu.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SesionMapper implements RowMapper<Sesion>{

	public Sesion mapRow(ResultSet rs, int arg1) throws SQLException {
		Sesion objeto = new Sesion();
		
		objeto.setSesionId(rs.getString("SESION_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFInicio(rs.getString("F_INICIO"));
		objeto.setFFinal(rs.getString("F_FINAL"));
		objeto.setIp(rs.getString("IP"));		
		objeto.setFinalizo(rs.getString("FINALIZO"));

		return objeto;
	}

}
