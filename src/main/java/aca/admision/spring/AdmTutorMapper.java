package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmTutorMapper implements RowMapper<AdmTutor> {
	@Override
	public AdmTutor mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmTutor objeto = new AdmTutor();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setTutor(rs.getString("TUTOR"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setCalle(rs.getString("CALLE"));
		objeto.setNumero(rs.getString("NUMERO"));
		objeto.setColonia(rs.getString("COLONIA"));
		objeto.setCodigoPostal(rs.getString("CODIGOPOSTAL"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setEstado(rs.getString("ESTADO"));		
		objeto.setCiudad(rs.getString("CIUDAD"));
		
		return objeto;
	}

}
