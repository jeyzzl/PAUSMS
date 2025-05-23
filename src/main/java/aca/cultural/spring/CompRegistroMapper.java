package aca.cultural.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompRegistroMapper implements RowMapper<CompRegistro> {
	public CompRegistro mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CompRegistro objeto = new CompRegistro();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setFechaRegistro(rs.getString("FECHA_REGISTRO"));

		return objeto;
	}
}