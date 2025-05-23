package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalConsejeroMapper implements RowMapper<SalConsejero>{
	
	public SalConsejero mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SalConsejero objeto = new SalConsejero();
		
		objeto.setSalidaId(rs.getString("SALIDA_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setConsejero(rs.getString("CONSEJERO"));
		objeto.setTrabajo(rs.getString("TRABAJO"));
		objeto.setClave(rs.getString("CLAVE"));
				
		return objeto;
	}
}