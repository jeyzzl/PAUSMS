package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatAsociacionMapper implements RowMapper<CatAsociacion> {
	public CatAsociacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatAsociacion asociacion = new CatAsociacion();
		asociacion.setDivisionId(rs.getString("DIVISION_ID"));
		asociacion.setUnionId(rs.getString("UNION_ID"));
		asociacion.setAsociacionId(rs.getString("ASOCIACION_ID"));
		asociacion.setNombreAsociacion(rs.getString("NOMBRE_ASOCIACION"));
		asociacion.setDireccion(rs.getString("DIRECCION"));
		asociacion.setCodPostal(rs.getString("COD_POSTAL"));		
		asociacion.setTelefono(rs.getString("TELEFONO"));
		asociacion.setFax(rs.getString("FAX"));
		asociacion.setEmail(rs.getString("EMAIL"));
		asociacion.setPaisId(rs.getString("PAIS_ID"));
		asociacion.setEstadoId(rs.getString("ESTADO_ID"));
		asociacion.setCiudadId(rs.getString("CIUDAD_ID"));
		
		return asociacion;
	}
}
