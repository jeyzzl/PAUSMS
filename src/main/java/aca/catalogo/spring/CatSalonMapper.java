package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatSalonMapper implements RowMapper<CatSalon> {
	public CatSalon mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatSalon objeto = new CatSalon();
		
		objeto.setEdificioId(rs.getString("EDIFICIO_ID"));
		objeto.setSalonId(rs.getString("SALON_ID"));
		objeto.setNombreSalon(rs.getString("NOMBRE_SALON"));
		objeto.setNumAlumnos(rs.getString("NUM_ALUMNOS"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}
