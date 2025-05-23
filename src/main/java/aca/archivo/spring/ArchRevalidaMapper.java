package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchRevalidaMapper implements RowMapper<ArchRevalida>{
	@Override
	public ArchRevalida mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchRevalida objeto = new ArchRevalida();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setRevalida(rs.getString("REVALIDA"));
		objeto.setFechaRevalida(rs.getString("FECHA_REVALIDA"));	
		
		return objeto;
	}
}