package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitModalidadMapper implements RowMapper<TitModalidad>{
	
	public TitModalidad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitModalidad objeto = new TitModalidad();
		
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setModalidadNombre(rs.getString("MODALIDAD_NOMBRE"));
		objeto.setModalidadTipo(rs.getString("MODALIDAD_TIPO"));
		
		return objeto;
	}
}