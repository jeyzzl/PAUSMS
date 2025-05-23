package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecAccesoMapper implements RowMapper<BecAcceso> {
	@Override
	public BecAcceso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecAcceso objeto = new BecAcceso();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));	
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));
		objeto.setFecha(rs.getString("FECHA"));	
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}
}
