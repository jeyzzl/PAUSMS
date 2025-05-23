package aca.pg.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PosArchGeneralMapper implements RowMapper<PosArchGeneral>{
	
	public PosArchGeneral mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PosArchGeneral objeto = new PosArchGeneral();		 
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setImagen(rs.getInt("IMAGEN"));		
		return objeto;
	}
}