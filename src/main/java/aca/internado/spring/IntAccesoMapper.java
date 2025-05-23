package aca.internado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IntAccesoMapper implements RowMapper<IntAcceso> {
	public IntAcceso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		IntAcceso objeto = new IntAcceso();
		
		objeto.setDormitorioId(rs.getString("DORMITORIO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setRol(rs.getString("ROL"));
		objeto.setPasillo(rs.getString("PASILLO"));		
		
		return objeto;
	}
}