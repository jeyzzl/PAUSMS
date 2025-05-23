package aca.valida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ValDocumentoMapper implements RowMapper<ValDocumento>{

	public ValDocumento mapRow(ResultSet rs, int arg1) throws SQLException {
		ValDocumento objeto = new ValDocumento();
		
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		
		return objeto;
	}

}
