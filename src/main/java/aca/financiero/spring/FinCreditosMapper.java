package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinCreditosMapper implements RowMapper<FinCreditos> {

	public FinCreditos mapRow(ResultSet rs, int rowNum) throws SQLException {
		FinCreditos objeto = new FinCreditos();
		
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setDeptoId(rs.getString("DEPTO_ID"));
		objeto.setNorAcfe(rs.getString("NOR_ACFE"));
		objeto.setNorNoAfce(rs.getString("NOR_NOACFE"));
		objeto.setDiaAcfe(rs.getString("DIA_ACFE"));
		objeto.setDiaNoAcfe(rs.getString("DIA_NOACFE"));
		
		return objeto;
	}

}
