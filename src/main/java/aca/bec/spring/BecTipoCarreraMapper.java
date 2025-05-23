package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecTipoCarreraMapper implements RowMapper<BecTipoCarrera> {
	
	@Override
	public BecTipoCarrera mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecTipoCarrera objeto = new BecTipoCarrera();
		
		objeto.setIdEjercicio(rs.getString("ID_EJERCICIO"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		
		return objeto;
	}
}
