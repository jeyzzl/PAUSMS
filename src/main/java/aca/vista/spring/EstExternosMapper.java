package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EstExternosMapper implements RowMapper<EstExternos>{

	public EstExternos mapRow(ResultSet rs, int arg1) throws SQLException {
		EstExternos objeto = new EstExternos();

		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setRazon(rs.getString("RAZON"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}

}