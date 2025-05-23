package aca.cultural.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompAsistenciaAlumnoMapper implements RowMapper<CompAsistenciaAlumno> {
	public CompAsistenciaAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CompAsistenciaAlumno objeto = new CompAsistenciaAlumno();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setEntrada(rs.getString("ENTRADA"));
		objeto.setSalida(rs.getString("SALIDA"));
		objeto.setEntradaHora(rs.getString("ENTRADA_HORA"));
		objeto.setSalidaHora(rs.getString("SALIDA_HORA"));

		return objeto;
	}
}