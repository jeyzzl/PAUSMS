package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinMaratumMapper implements RowMapper<FinMaratum> {

	public FinMaratum mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FinMaratum objeto = new FinMaratum();
		
		objeto.setId(rs.getString("ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setArchivo(rs.getBytes("ARCHIVO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
