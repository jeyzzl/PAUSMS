package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinBloqueoMapper implements RowMapper<FinBloqueo> {

	public FinBloqueo mapRow(ResultSet rs, int rowNum) throws SQLException{
		FinBloqueo objeto = new FinBloqueo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));		
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}

}
