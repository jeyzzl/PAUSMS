package aca.voto.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VotoEventoMapper implements RowMapper<VotoEvento>{

	public VotoEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		VotoEvento objeto = new VotoEvento();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setEventoNombre(rs.getString("EVENTO_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setPoblacion(rs.getString("POBLACION"));
		
		return objeto;
	}

}
