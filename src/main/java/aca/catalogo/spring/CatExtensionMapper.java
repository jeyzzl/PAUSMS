package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatExtensionMapper implements RowMapper<CatExtension> {
	public CatExtension mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatExtension objeto = new CatExtension();
		
		objeto.setExtensionId(rs.getString("EXTENSION_ID"));
		objeto.setNombreExtension(rs.getString("NOMBRE_EXTENSION"));
		objeto.setReferente(rs.getString("REFERENTE"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setColonia(rs.getString("COLONIA"));
		objeto.setCodPostal(rs.getString("COD_POSTAL"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setFax(rs.getString("FAX"));
		objeto.setEmail(rs.getString("EMAIL"));		
		
		return objeto;
	}
}
