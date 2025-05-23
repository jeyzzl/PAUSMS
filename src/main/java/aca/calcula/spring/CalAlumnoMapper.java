package aca.calcula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CalAlumnoMapper implements RowMapper<CalAlumno> {
	public CalAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CalAlumno objeto = new CalAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setNumPagare(rs.getString("NUM_PAGARE"));
		objeto.setConfirmar(rs.getString("CONFIRMAR"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCobroMatricula(rs.getString("COBRO_MATRICULA"));
		objeto.setSaldo(rs.getString("SALDO"));
		objeto.setPorcentaje(rs.getString("PORCENTAJE"));
				
		return objeto;
	}
}
