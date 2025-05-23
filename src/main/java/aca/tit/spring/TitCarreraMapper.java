package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitCarreraMapper implements RowMapper<TitCarrera>{
	public TitCarrera mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitCarrera objeto = new TitCarrera();
		
		objeto.setCveCarrera(rs.getString("CVECARRERA"));
		objeto.setNombreCarrera(rs.getString("NOMBRECARRERA"));
		objeto.setFechaInicio(rs.getString("FECHAINICIO"));
		objeto.setFechaTerminacion(rs.getString("FECHATERMINACION"));
		objeto.setIdAutorizacion(rs.getString("IDAUTORIZACION"));
		objeto.setAutorizacion(rs.getString("AUTORIZACION"));
		objeto.setNumeroRvoe(rs.getString("NUMERORVOE"));
		objeto.setFolio(rs.getString("FOLIO"));
		
		return objeto;
	}
}