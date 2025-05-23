package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpConfirmarMapper implements RowMapper<EmpConfirmar>{
	
	public EmpConfirmar mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpConfirmar objeto = new EmpConfirmar();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setFecha(rs.getString("FECHA"));		
		
		return objeto;
	}
}