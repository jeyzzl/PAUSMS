package aca.log.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LogProcesoMapper implements RowMapper<LogProceso> {

	public LogProceso mapRow(ResultSet rs, int rowNum) throws SQLException {
		LogProceso objeto = new LogProceso();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setModulo(rs.getString("MODULO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEvento(rs.getString("EVENTO"));
		
		return objeto;
	}

}
