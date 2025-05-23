package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmAsesorMapper implements RowMapper<AdmAsesor> {
	@Override
	public AdmAsesor mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmAsesor objeto = new AdmAsesor();
		
		objeto.setAsesorId(rs.getString("ASESOR_ID"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setChat(rs.getString("CHAT"));
		objeto.setTelefono(rs.getString("TELEFONO"));	
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
