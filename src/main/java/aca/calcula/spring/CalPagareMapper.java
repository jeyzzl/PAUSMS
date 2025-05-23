package aca.calcula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CalPagareMapper implements RowMapper<CalPagare> {
	public CalPagare mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CalPagare objeto = new CalPagare();
		
		objeto.setPagareId(rs.getString("PAGARE_ID"));
		objeto.setPagareNombre(rs.getString("PAGARE_NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
				
		return objeto;
	}
}
