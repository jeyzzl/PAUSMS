package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumHermanosMapper implements RowMapper<AlumHermanos>{
	@Override
	public AlumHermanos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumHermanos objeto = new AlumHermanos();		
		objeto.setFamiliaId(rs.getString("FAMILIA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}