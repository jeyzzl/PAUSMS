package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmPruebaAlumnoMapper implements RowMapper<AdmPruebaAlumno> {
	@Override
	public AdmPruebaAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmPruebaAlumno objeto = new AdmPruebaAlumno();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setPruebaId(rs.getString("PRUEBA_ID"));
		objeto.setUsuario(rs.getString("USUARIO"));		
		objeto.setFecha(rs.getString("FECHA"));

		return objeto;
	}

}
