package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmPruebaMapper implements RowMapper<AdmPrueba>{
	@Override
	public AdmPrueba mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AdmPrueba objeto = new AdmPrueba();
		
		objeto.setPruebaId(rs.getString("PRUEBA_ID"));				
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setAccion(rs.getString("ACCION"));
		
		return objeto;
	}
}