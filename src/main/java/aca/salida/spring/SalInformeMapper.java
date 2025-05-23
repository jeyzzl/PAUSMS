package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalInformeMapper implements RowMapper<SalInforme> {

	public SalInforme mapRow(ResultSet rs, int rowNum) throws SQLException {
		SalInforme objeto = new SalInforme();
		
		objeto.setSalidaId(rs.getString("SALIDA_ID"));
		objeto.setIncidentes(rs.getString("INCIDENTES"));
		objeto.setObservaciones(rs.getString("OBSERVACIONES"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}
}
