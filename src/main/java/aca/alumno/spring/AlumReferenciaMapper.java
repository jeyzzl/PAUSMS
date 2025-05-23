package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumReferenciaMapper implements RowMapper<AlumReferencia>{
	@Override
	public AlumReferencia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumReferencia objeto = new AlumReferencia();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setScotiabank(rs.getString("SCOTIABANK"));
		objeto.setBanamex(rs.getString("BANAMEX"));
		objeto.setSantander(rs.getString("SANTANDER"));
		
		return objeto;
	}
}
