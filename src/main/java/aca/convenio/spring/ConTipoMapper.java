package aca.convenio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConTipoMapper implements RowMapper<ConTipo> {
	public ConTipo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConTipo evento = new ConTipo();
		
		evento.setTipoId(rs.getString("TIPO_ID"));
		evento.setTipoNombre(rs.getString("TIPO_NOMBRE"));
		
		return evento;
	}
}
