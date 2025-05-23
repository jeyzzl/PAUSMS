package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalAutoMapperCorto implements RowMapper<SalAuto>{
	
	public SalAuto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SalAuto objeto = new SalAuto();
		
		objeto.setSalidaId(rs.getString("SALIDA_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setPoliza(rs.getString("POLIZA"));
		objeto.setTelefono(rs.getString("TELEFONO"));		
		
		return objeto;
	}
}