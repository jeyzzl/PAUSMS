package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmPadresMapper implements RowMapper<AdmPadres> {

	@Override
	public AdmPadres mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmPadres objeto = new AdmPadres();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setPadreNombre(rs.getString("PADRE_NOMBRE"));
		objeto.setPadreApellido(rs.getString("PADRE_APELLIDO"));
		objeto.setPadreReligion(rs.getString("PADRE_RELIGION"));
		objeto.setPadreNacionalidad(rs.getString("PADRE_NACIONALIDAD"));
		objeto.setPadreOcupacion(rs.getString("PADRE_OCUPACION"));
		objeto.setMadreNombre(rs.getString("MADRE_NOMBRE"));
		objeto.setMadreApellido(rs.getString("MADRE_APELLIDO"));
		objeto.setMadreReligion(rs.getString("MADRE_RELIGION"));
		objeto.setMadreNacionalidad(rs.getString("MADRE_NACIONALIDAD"));
		objeto.setMadreOcupacion(rs.getString("MADRE_OCUPACION"));
		
		return objeto;
	}

}
