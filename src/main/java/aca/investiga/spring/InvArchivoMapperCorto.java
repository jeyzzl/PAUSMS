package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvArchivoMapperCorto implements RowMapper<InvArchivo> {
	public InvArchivo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		InvArchivo objeto = new InvArchivo();
		
		objeto.setProyectoId(rs.getString("PROYECTO_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setNombre(rs.getString("NOMBRE"));	
		
		return objeto;
	}
}