package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpUsuarioTelMapper implements RowMapper<EmpUsuarioTel> {
	public EmpUsuarioTel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpUsuarioTel objeto = new EmpUsuarioTel();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTelefono(rs.getString("TELEFONO"));	
		objeto.setExtension(rs.getString("EXTENSION"));
		objeto.setLugar(rs.getString("LUGAR"));
		
		return objeto;
	}
}
