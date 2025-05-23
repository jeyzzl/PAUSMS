package aca.candado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class CandPermisoMapper implements RowMapper<CandPermiso> {
	public CandPermiso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CandPermiso permiso = new CandPermiso();
		
		permiso.setTipoId(rs.getString("TIPO_ID"));
		permiso.setCodioPersonal(rs.getString("CODIGO_PERSONAL"));
		permiso.setUsAlta(rs.getString("US_ALTA"));
		permiso.setFechaAlta(rs.getString("FECHA_ALTA"));
				
		return permiso;
	}
}
