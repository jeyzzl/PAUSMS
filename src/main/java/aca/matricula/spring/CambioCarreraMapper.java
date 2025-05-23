package aca.matricula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CambioCarreraMapper implements RowMapper<CambioCarrera> {

	public CambioCarrera mapRow(ResultSet rs, int rowNum) throws SQLException {
		CambioCarrera objeto = new CambioCarrera();
		
		objeto.setSolicitudId(rs.getString("SOLICITUD_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCarreraBaja(rs.getString("CARRERA_BAJA"));
		objeto.setCarreraAlta(rs.getString("CARRERA_ALTA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}

}
