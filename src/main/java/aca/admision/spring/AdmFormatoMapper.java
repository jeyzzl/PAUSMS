package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmFormatoMapper implements RowMapper<AdmFormato> {
	@Override
	public AdmFormato mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmFormato objeto = new AdmFormato();
		
		objeto.setFormatoId(rs.getString("FORMATO_ID"));
		objeto.setFormatoNombre(rs.getString("FORMATO_NOMBRE"));
		objeto.setArchivo(rs.getString("ARCHIVO"));
		
		return objeto;
	}

}
