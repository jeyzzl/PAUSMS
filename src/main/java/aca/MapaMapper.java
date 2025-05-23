package aca;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaMapper implements RowMapper<Mapa> {
	public Mapa mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Mapa objeto = new Mapa();
		
		objeto.setLlave(rs.getString("LLAVE"));
		objeto.setValor(rs.getString("VALOR"));
		
		return objeto;
	}
}
