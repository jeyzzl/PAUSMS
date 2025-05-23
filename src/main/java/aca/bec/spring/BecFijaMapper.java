package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecFijaMapper implements RowMapper<BecFija> {
	
	@Override
	public BecFija mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecFija objeto = new BecFija();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setIdCcosto(rs.getString("ID_CCOSTO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}
}
