package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmOpcionesMapper implements RowMapper<AdmOpciones> {
	@Override
	public AdmOpciones mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmOpciones objeto = new AdmOpciones();
		
		objeto.setOpcionId(rs.getString("OPCION_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));

		return objeto;
	}

}
