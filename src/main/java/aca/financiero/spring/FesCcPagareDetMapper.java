package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FesCcPagareDetMapper  implements RowMapper<FesCcPagareDet> {

	public FesCcPagareDet mapRow(ResultSet rs, int rowNum) throws SQLException {
		FesCcPagareDet objeto = new FesCcPagareDet();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloque(rs.getString("BLOQUE"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setfVencimiento(rs.getString("FVENCIMIENTO"));
		objeto.setImporte(rs.getString("IMPORTE"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setcCobroId(rs.getString("CCOBRO_ID"));
		objeto.setId(rs.getString("ID"));

		return objeto;
	}

}
