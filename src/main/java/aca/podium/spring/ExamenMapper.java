package aca.podium.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExamenMapper implements RowMapper<Examen>{
	
	public Examen mapRow(ResultSet rs, int arg1) throws SQLException {	
		
		Examen objeto = new Examen();
		
		objeto.setId(rs.getInt("ID"));
		objeto.setFolio(rs.getInt("FOLIO"));
		objeto.setFecha(rs.getDate("FECHA"));			
		objeto.setInicio(rs.getString("INICIO"));
		objeto.setTermino(rs.getString("TERMINO"));
		objeto.setTiempo(rs.getString("TIEMPO"));
		objeto.setActivo(rs.getBoolean("ACTIVO"));
		
		return objeto;
	}
}