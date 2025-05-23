package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FesContratoFinancieroMapper  implements RowMapper<FesContratoFinanciero> {

	public FesContratoFinanciero mapRow(ResultSet rs, int rowNum) throws SQLException {
		FesContratoFinanciero objeto = new FesContratoFinanciero();
		
		objeto.setId(rs.getString("ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setFechaVencimiento(rs.getString("FECHA_VENCIMIENTO"));
		objeto.setImporte(rs.getString("IMPORTE"));
		objeto.setLiquidado(rs.getString("LIQUIDADO"));
		objeto.setFechaLiquidacion(rs.getString("FECHA_LIQUIDACION"));
		objeto.setObservaciones(rs.getString("OBSERVACIONES"));

		return objeto;
	}

}
