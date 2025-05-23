package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatDivisionMapper implements RowMapper<CatDivision> {
	public CatDivision mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatDivision objeto = new CatDivision();
		
		objeto.setDivisionId(rs.getString("DIVISION_ID"));
		objeto.setNombreDivision(rs.getString("NOMBRE_DIVISION"));	
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setColonia(rs.getString("COLONIA"));
		objeto.setCodPostal(rs.getString("COD_POSTAL"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setFax(rs.getString("FAX"));
		objeto.setEmail(rs.getString("EMAIL"));		
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		objeto.setNombreCorto(rs.getString("NOMBRE_CORTO"));
		
		return objeto;
	}
}
