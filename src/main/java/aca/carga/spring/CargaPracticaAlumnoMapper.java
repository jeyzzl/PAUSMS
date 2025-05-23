package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaPracticaAlumnoMapper implements RowMapper<CargaPracticaAlumno> {

	public CargaPracticaAlumno mapRow(ResultSet rs, int rowNum) throws SQLException {
		CargaPracticaAlumno objeto = new CargaPracticaAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCargaId(rs.getString("CARGA_ID"));		
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));		
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
