package aca.menu.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ModuloAyudaMapper  implements RowMapper<ModuloAyuda> {
	public ModuloAyuda mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ModuloAyuda objeto = new ModuloAyuda();
		
		objeto.setOpcionId(rs.getString("OPCION_ID"));
		objeto.setAyudaId(rs.getString("AYUDA_ID"));
		objeto.setAyuda(rs.getString("AYUDA"));
		
		return objeto;
	}	
}