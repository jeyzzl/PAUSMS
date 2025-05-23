package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvReferenteMapper implements RowMapper<InvReferente> {
	public InvReferente mapRow(ResultSet rs, int arg1) throws SQLException {
		
		InvReferente objeto = new InvReferente();
		
		objeto.setCodigoId(rs.getString("CODIGO_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		
		return objeto;
	}
}