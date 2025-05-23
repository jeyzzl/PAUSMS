package aca.calcula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CalMovimientoMapper implements RowMapper<CalMovimiento> {
	public CalMovimiento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CalMovimiento objeto = new CalMovimiento();
		
		objeto.setMovimientoId(rs.getString("MOVTO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setConceptoId(rs.getString("CONCEPTO_ID"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setImporte(rs.getString("IMPORTE"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setFecha(rs.getString("FECHA"));
				
		return objeto;
	}
}
