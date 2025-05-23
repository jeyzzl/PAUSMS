package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmRecomiendaMapper implements RowMapper<AdmRecomienda> {
	@Override
	public AdmRecomienda mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmRecomienda objeto = new AdmRecomienda();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setRecomendacionId(rs.getString("RECOMENDACION_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setPuesto(rs.getString("PUESTO"));
		objeto.setEmail(rs.getString("EMAIL"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		
		return objeto;
	}

}
