package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumDireccionMapper implements RowMapper<AlumDireccion>{
	@Override
	public AlumDireccion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumDireccion objeto = new AlumDireccion();	
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCalle(rs.getString("CALLE"));
		objeto.setColonia(rs.getString("COLONIA"));
		objeto.setCodPostal(rs.getString("COD_POSTAL"));
		objeto.setApartado(rs.getString("APARTADO"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setNumero(rs.getString("NUMERO"));
		
		return objeto;
	}
}
