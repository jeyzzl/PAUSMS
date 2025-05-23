package aca.calcula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CalPagareAlumnoMapper implements RowMapper<CalPagareAlumno> {
	public CalPagareAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CalPagareAlumno objeto = new CalPagareAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPagareId(rs.getString("PAGARE_ID"));
		objeto.setPagareNombre(rs.getString("PAGARE_NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setImporte(rs.getString("IMPORTE"));
				
		return objeto;
	}
}
