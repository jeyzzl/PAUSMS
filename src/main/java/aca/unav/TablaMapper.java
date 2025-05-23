package aca.unav;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TablaMapper implements RowMapper<Tabla> {

	public Tabla mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tabla objeto = new Tabla();
		
		objeto.setUsuario(rs.getString("OWNER"));
		objeto.setTableName(rs.getString("TABLE_NAME"));
		
		return objeto;
	}

}
