package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class AlumBancoMapper implements RowMapper<AlumBanco> {

public AlumBanco mapRow(ResultSet rs, int arg1) throws SQLException {
		
	AlumBanco objeto = new AlumBanco();		
		
		objeto.setBancoId(rs.getString("BANCO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setBanco(rs.getString("BANCO"));
		objeto.setBancoRama(rs.getString("BANCO_RAMA"));
		objeto.setCuentaNombre(rs.getString("CUENTA_NOMBRE"));
		objeto.setCuentaNumero(rs.getString("CUENTA_NUMERO"));
		objeto.setNumeroBBS(rs.getString("NUMERO_BBS"));
		objeto.setCuentaTipo(rs.getString("CUENTA_TIPO"));
		objeto.setCodigoSwift(rs.getString("CODIGO_SWIFT"));
		
		return objeto;
}
}
