package aca.federacion.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FedEventosMapper implements RowMapper<FedEventos>{
	
	@Override
	public FedEventos mapRow(ResultSet rs, int rowNum) throws SQLException {
		FedEventos objeto = new FedEventos();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setEventoNombre(rs.getString("EVENTO_NOMBRE"));
		objeto.setEventoDescripcion(rs.getString("EVENTO_DESCRIPCION"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}

}
