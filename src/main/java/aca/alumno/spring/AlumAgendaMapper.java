package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumAgendaMapper implements RowMapper<AlumAgenda>{
	@Override
	public AlumAgenda mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumAgenda objeto = new AlumAgenda();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setEntregado(rs.getString("ENTREGADO"));
		objeto.setFecha(rs.getString("FECHA"));	
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		
		return objeto;
	}
}