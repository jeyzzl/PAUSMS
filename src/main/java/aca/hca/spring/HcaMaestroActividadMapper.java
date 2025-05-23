package aca.hca.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HcaMaestroActividadMapper implements RowMapper<HcaMaestroActividad> {
	public HcaMaestroActividad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		HcaMaestroActividad objeto = new HcaMaestroActividad();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setSemanas(rs.getString("SEMANAS"));
		
		return objeto;
	}
}