package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchStatusMapper implements RowMapper<ArchStatus>{
	@Override
	public ArchStatus mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchStatus objeto = new ArchStatus();
		
		objeto.setIdStatus(rs.getString("IDSTATUS"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		
		return objeto;
	}
}