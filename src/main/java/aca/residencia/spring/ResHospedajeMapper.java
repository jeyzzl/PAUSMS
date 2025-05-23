package aca.residencia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResHospedajeMapper implements RowMapper<ResHospedaje> {

	public ResHospedaje mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResHospedaje objeto = new ResHospedaje();
		
		objeto.setId(rs.getString("ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidos(rs.getString("APELLIDOS"));
		objeto.setNomina(rs.getString("NOMINA"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setCupoHombres(rs.getString("CUPO_HOMBRES"));
		objeto.setCupoMujeres(rs.getString("CUPO_MUJERES"));
		objeto.setCuartos(rs.getString("CUARTOS"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
