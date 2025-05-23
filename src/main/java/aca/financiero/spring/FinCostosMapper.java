package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinCostosMapper implements RowMapper<FinCostos> {

	public FinCostos mapRow(ResultSet rs, int rowNum) throws SQLException {
		FinCostos objeto = new FinCostos();
		
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setInternado(rs.getString("INTERNADO"));
		objeto.setComedor(rs.getString("COMEDOR"));
		objeto.setMatMex(rs.getString("MAT_MEX"));
		objeto.setMatExt(rs.getString("MAT_EXT"));
		objeto.setPagare(rs.getString("PAGARE"));
		
		return objeto;
	}

}
