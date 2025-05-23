package aca.ssoc.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SsocAsignacionMapper implements RowMapper<SsocAsignacion>{
	
	public SsocAsignacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SsocAsignacion objeto = new SsocAsignacion();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setAsignacionId(rs.getString("ASIGNACION_ID"));
		objeto.setDependencia(rs.getString("DEPENDENCIA"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setResponsable(rs.getString("RESPONSABLE"));
		objeto.setFechaInicio(rs.getString("F_INICIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setSector(rs.getString("SECTOR"));
		
		return objeto;
	}
}