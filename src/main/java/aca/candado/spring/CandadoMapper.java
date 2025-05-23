package aca.candado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class CandadoMapper implements RowMapper<Candado> {
	public Candado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Candado candado = new Candado();
		
		candado.setCandadoId(rs.getString("CANDADO_ID"));
		candado.setTipoId(rs.getString("TIPO_ID"));
		candado.setNombreCandado(rs.getString("NOMBRE_CANDADO"));
				
		return candado;
	}
}
