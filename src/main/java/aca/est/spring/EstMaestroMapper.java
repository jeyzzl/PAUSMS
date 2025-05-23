package aca.est.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EstMaestroMapper implements RowMapper<EstMaestro>{
	
	public EstMaestro mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EstMaestro objeto = new EstMaestro();		
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));		
		objeto.setImporte(rs.getString("IMPORTE"));		
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}
}