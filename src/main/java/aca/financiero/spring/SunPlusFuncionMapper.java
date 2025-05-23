package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SunPlusFuncionMapper implements RowMapper<SunPlusFuncion> {

	public SunPlusFuncion mapRow(ResultSet rs, int arg1) throws SQLException {
		SunPlusFuncion objeto = new SunPlusFuncion();
		
		objeto.setDepartamento(rs.getString("DEPARTAMENTO"));
		objeto.setFuncion(rs.getString("FUNCION_SP"));		

		return objeto;
	}

}
