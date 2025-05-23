package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalBitacoraMapper implements RowMapper<SalBitacora>{
	
	public SalBitacora mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SalBitacora objeto = new SalBitacora();
		
		objeto.setSalidaId(rs.getString("SALIDA_ID"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFecha(rs.getString("FECHA"));
				
		return objeto;
	}
}