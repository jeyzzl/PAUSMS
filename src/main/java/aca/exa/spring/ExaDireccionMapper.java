package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaDireccionMapper implements RowMapper<ExaDireccion>{
	
	public ExaDireccion mapRow(ResultSet rs, int arg1) throws SQLException{
		
		ExaDireccion objeto = new ExaDireccion();
		
		objeto.setDireccionId(rs.getString("DIRECCION_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setCiudad(rs.getString("CIUDAD"));
		objeto.setCp(rs.getString("CP"));
		objeto.setEliminado(rs.getString("ELIMINADO"));		
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setFechaActualizacion(rs.getString("FECHAACTUALIZACION"));
		objeto.setIdDireccion(rs.getString("IDDIRECCION"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		
		return objeto;	
	}
}