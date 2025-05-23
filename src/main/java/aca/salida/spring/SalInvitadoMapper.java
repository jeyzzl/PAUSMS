package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalInvitadoMapper implements RowMapper<SalInvitado> {

	public SalInvitado mapRow(ResultSet rs, int rowNum) throws SQLException {
		SalInvitado objeto = new SalInvitado();
		
		objeto.setSalidaId(rs.getString("SALIDA_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}

}
