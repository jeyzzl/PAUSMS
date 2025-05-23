package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitTramiteDocMapper implements RowMapper<TitTramiteDoc> {

	public TitTramiteDoc mapRow(ResultSet rs, int rowNum) throws SQLException {
		TitTramiteDoc objeto = new TitTramiteDoc();
		
		objeto.setTramiteId(rs.getString("TRAMITE_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}

}
