package aca.unav;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ColumnaMapper implements RowMapper<Columna> {

	public Columna mapRow(ResultSet rs, int rowNum) throws SQLException {
		Columna objeto = new Columna();
		
		objeto.setOwner(rs.getString("OWNER"));
		objeto.setTableName(rs.getString("TABLE_NAME"));
		objeto.setColumnName(rs.getString("COLUMN_NAME"));
		objeto.setDataType(rs.getString("DATA_TYPE"));
		objeto.setDataLength(rs.getString("DATA_LENGTH"));
		objeto.setDataPrecision(rs.getString("DATA_PRECISION"));
		objeto.setDataDefault(rs.getString("DATA_DEFAULT"));
		objeto.setDataScale(rs.getString("DATA_SCALE"));
		objeto.setNullable(rs.getString("NULLABLE"));		
		
		return objeto;
	}

}
