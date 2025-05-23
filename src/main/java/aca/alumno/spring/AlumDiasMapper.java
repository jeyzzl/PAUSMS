package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumDiasMapper implements RowMapper<AlumDias>{
	@Override
	public AlumDias mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumDias objeto = new AlumDias();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setDias(rs.getString("DIAS"));	
			
		return objeto;
	}
}