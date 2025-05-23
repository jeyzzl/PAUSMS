package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchDocAlumMapper implements RowMapper<ArchDocAlum>{
	@Override
	public ArchDocAlum mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchDocAlum objeto = new ArchDocAlum();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setIdDocumento(rs.getString("IDDOCUMENTO"));
		objeto.setIdStatus(rs.getString("IDSTATUS"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCantidad(rs.getString("CANTIDAD"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setUbicacion(rs.getString("UBICACION"));
		objeto.setIncorrecto(rs.getString("INCORRECTO"));
		
		return objeto;
	}
}