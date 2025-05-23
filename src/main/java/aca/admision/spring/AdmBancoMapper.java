package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmBancoMapper implements RowMapper<AdmBanco>{

	@Override
	public AdmBanco mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmBanco objeto = new AdmBanco();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setBanco(rs.getString("BANCO"));
		objeto.setBancoRama(rs.getString("BANCO_RAMA"));
		objeto.setCuentaNombre(rs.getString("CUENTA_NOMBRE"));
		objeto.setCuentaNumero(rs.getString("CUENTA_NUMERO"));
		objeto.setNumeroBbs(rs.getString("NUMERO_BBS"));
		objeto.setCuentaTipo(rs.getString("CUENTA_TIPO"));
		objeto.setCodigoSwift(rs.getString("CODIGO_SWIFT"));
		
		return objeto;
	}
	
}