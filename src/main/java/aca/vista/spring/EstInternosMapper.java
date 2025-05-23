package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EstInternosMapper implements RowMapper<EstInternos>{

	public EstInternos mapRow(ResultSet rs, int arg1) throws SQLException {
		EstInternos objeto = new EstInternos();

		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setDormitorio(rs.getString("DORMITORIO"));
		
		return objeto;
	}

}