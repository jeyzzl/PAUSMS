package aca.cred.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CredVisitanteMapper implements RowMapper<CredVisitante> {
	
	public CredVisitante mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CredVisitante objeto = new CredVisitante();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setPaterno(rs.getString("PATERNO"));
		objeto.setMaterno(rs.getString("MATERNO"));
		objeto.setRfid(rs.getString("RFID"));
		objeto.setRfidTag(rs.getString("RFID_TAG"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}

}
