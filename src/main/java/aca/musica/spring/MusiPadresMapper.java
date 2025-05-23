package aca.musica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MusiPadresMapper implements RowMapper<MusiPadres> {
	
	@Override
	public MusiPadres mapRow(ResultSet rs, int rowNum) throws SQLException {
		MusiPadres objeto = new MusiPadres();
		
		objeto.setCodigoId(rs.getString("CODIGO_ID"));
		objeto.setPadNombre(rs.getString("PAD_NOMBRE"));
		objeto.setPadPaterno(rs.getString("PAD_PATERNO"));
		objeto.setPadMaterno(rs.getString("PAD_MATERNO"));
		objeto.setPadDireccion(rs.getString("PAD_DIRECCION"));
		objeto.setPadCorreo(rs.getString("PAD_CORREO"));
		objeto.setPadOcupacion(rs.getString("PAD_OCUPACION"));
		objeto.setPadTelcasa(rs.getString("PAD_TELCASA"));
		objeto.setPadTeltrabajo(rs.getString("PAD_TELTRABAJO"));
		objeto.setPadTelcelular(rs.getString("PAD_TELCELULAR"));
		objeto.setMadNombre(rs.getString("MAD_NOMBRE"));
		objeto.setMadPaterno(rs.getString("MAD_PATERNO"));
		objeto.setMadMaterno(rs.getString("MAD_MATERNO"));
		objeto.setMadDireccion(rs.getString("MAD_DIRECCION"));
		objeto.setMadCorreo(rs.getString("MAD_CORREO"));
		objeto.setMadOcupacion(rs.getString("MAD_OCUPACION"));
		objeto.setMadTelcasa(rs.getString("MAD_TELCASA"));
		objeto.setMadTeltrabajo(rs.getString("MAD_TELTRABAJO"));
		objeto.setMadTelcelular(rs.getString("MAD_TELCELULAR"));
		objeto.setCodigoUsuario(rs.getString("CODIGO_USUARIO"));
		objeto.setPadVive(rs.getString("PAD_VIVE"));
		objeto.setMadVive(rs.getString("MAD_VIVE"));
		objeto.setPadReligionId(rs.getString("PAD_RELIGION_ID"));
		objeto.setMadReligionId(rs.getString("MAD_RELIGION_ID"));
		
		return objeto;
	}

}
