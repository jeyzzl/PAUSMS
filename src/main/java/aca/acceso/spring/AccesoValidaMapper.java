package aca.acceso.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccesoValidaMapper implements RowMapper<AccesoValida> {

	public AccesoValida mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccesoValida objeto = new AccesoValida();
		
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setLlave(rs.getString("LLAVE"));
		objeto.setCodigo(rs.getString("CODIGO"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}

}
