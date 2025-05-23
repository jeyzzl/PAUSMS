package aca.candado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class CandTipoMapper implements RowMapper<CandTipo> {
	public CandTipo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CandTipo candado = new CandTipo();
		
		candado.setTipoId(rs.getString("TIPO_ID"));
		candado.setNombreTipo(rs.getString("NOMBRE_TIPO"));
		candado.setResponsable(rs.getString("RESPONSABLE"));
		candado.setLugar(rs.getString("LUGAR"));
		candado.setTelefono(rs.getString("TELEFONO"));
		candado.setEstado(rs.getString("ESTADO"));
		candado.setCorreo(rs.getString("CORREO"));
		candado.setCelular(rs.getString("CELULAR"));
		candado.setPersona(rs.getString("PERSONA"));
				
		return candado;
	}
}
