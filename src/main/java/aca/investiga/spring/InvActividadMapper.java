package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvActividadMapper implements RowMapper<InvActividad> {

	public InvActividad mapRow(ResultSet rs, int arg1) throws SQLException {
		InvActividad objeto = new InvActividad();
		
		objeto.setProyectoId(rs.getString("PROYECTO_ID"));
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));
		objeto.setActividadNombre(rs.getString("ACTIVIDAD_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		
		return objeto;
	}

}
