package aca.parametros.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ParametrosMapper implements RowMapper<Parametros> {
	public Parametros mapRow(ResultSet rs, int arg1) throws SQLException {
		Parametros objeto = new Parametros();
		
		objeto.setId(rs.getString("ID"));
		objeto.setInstitucion(rs.getString("INSTITUCION")); 		
		objeto.setCertificados(rs.getString("CERTIFICADOS"));
		objeto.setConstancias(rs.getString("CONSTANCIAS"));
		objeto.setCardex(rs.getString("CARDEX"));
		objeto.setMonitor(rs.getString("MONITOR"));
		objeto.setMonitorRuta(rs.getString("MONITOR_RUTA"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		
		return objeto;
	}
}
