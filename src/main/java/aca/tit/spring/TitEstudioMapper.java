package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitEstudioMapper implements RowMapper<TitEstudio>{
	public TitEstudio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitEstudio objeto = new TitEstudio();
		
		objeto.setEstudioId(rs.getString("ESTUDIO_ID"));
		objeto.setEstudioNombre(rs.getString("ESTUDIO_NOMBRE"));
		objeto.setEstudioTipo(rs.getString("ESTUDIO_TIPO"));
		
		return objeto;
	}
}