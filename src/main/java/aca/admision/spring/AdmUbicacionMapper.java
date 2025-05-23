package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmUbicacionMapper implements RowMapper<AdmUbicacion> {
	@Override
	public AdmUbicacion mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmUbicacion objeto = new AdmUbicacion();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		objeto.setCalle(rs.getString("CALLE"));
		objeto.setNumero(rs.getString("NUMERO"));
		objeto.setCodigoPostal(rs.getString("CODIGO_POSTAL"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setColonia(rs.getString("COLONIA"));
		
		return objeto;
	}

}
