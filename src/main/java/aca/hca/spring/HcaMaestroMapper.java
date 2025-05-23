package aca.hca.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HcaMaestroMapper implements RowMapper<HcaMaestro> {
	public HcaMaestro mapRow(ResultSet rs, int arg1) throws SQLException {
		
		HcaMaestro objeto = new HcaMaestro();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}