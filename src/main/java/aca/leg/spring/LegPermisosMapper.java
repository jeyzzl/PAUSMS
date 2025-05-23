package aca.leg.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LegPermisosMapper implements RowMapper<LegPermisos> {

	public LegPermisos mapRow(ResultSet rs, int rowNum) throws SQLException {
		LegPermisos objeto = new LegPermisos();
		
		objeto.setCodigo(rs.getString("CODIGO"));
		objeto.setUsuarioAlta(rs.getString("USUARIO_ALTA"));
		objeto.setUsuarioBaja(rs.getString("USUARIO_BAJA"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));	
		objeto.setFechaLim(rs.getString("FECHA_LIM"));	
		objeto.setStatus(rs.getString("STATUS"));	
		objeto.setFolio(rs.getString("FOLIO"));	
		
		return objeto;
	}

}
