package aca.acceso.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccesoConfirmarMapper implements RowMapper<AccesoConfirmar>{
	@Override
	public AccesoConfirmar mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AccesoConfirmar objeto = new AccesoConfirmar();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		
		return objeto;
	}
}