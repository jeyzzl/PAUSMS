package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitCadenaMapper implements RowMapper<TitCadena> {
	public TitCadena mapRow(ResultSet rs, int rowNum) throws SQLException {
		TitCadena objeto = new TitCadena();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCadena(rs.getString("CADENA"));
		objeto.setSello(rs.getString("SELLO"));
		
		return objeto;
	}

}
