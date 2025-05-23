package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatModalidadMapper implements RowMapper<CatModalidad> {
	public CatModalidad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatModalidad objeto = new CatModalidad();
		
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setNombreModalidad(rs.getString("NOMBRE_MODALIDAD"));
		objeto.setEnLinea(rs.getString("ENLINEA"));
		objeto.setAdmisible(rs.getString("ADMISIBLE"));
		
		return objeto;
	}
}
