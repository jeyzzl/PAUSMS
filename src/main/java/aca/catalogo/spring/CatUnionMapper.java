package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatUnionMapper implements RowMapper<CatUnion> {
	public CatUnion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatUnion objeto = new CatUnion();
		
		objeto.setPaisId(rs.getString("DIVISION_ID"));
		objeto.setUnionId(rs.getString("UNION_ID"));
		objeto.setNombreUnion(rs.getString("NOMBRE_UNION"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setColonia(rs.getString("COLONIA"));
		objeto.setCodPostal(rs.getString("COD_POSTAL"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setFax(rs.getString("FAX"));
		objeto.setEmail(rs.getString("EMAIL"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		
		return objeto;
	}
}
