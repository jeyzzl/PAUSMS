package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvResultadoMapper implements RowMapper<InvResultado> {
	public InvResultado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		InvResultado objeto = new InvResultado();
		
		objeto.setProyectoId(rs.getString("PROYECTO_ID"));
		objeto.setInfraestructura(rs.getString("INFRAESTRUCTURA"));
		objeto.setBibliografia(rs.getString("BIBLIOGRAFIA"));
		
		return objeto;
	}
}