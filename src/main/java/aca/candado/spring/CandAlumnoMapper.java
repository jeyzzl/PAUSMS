package aca.candado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CandAlumnoMapper implements RowMapper<CandAlumno> {
	public CandAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CandAlumno CandAlumno = new CandAlumno();		
		CandAlumno.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		CandAlumno.setFolio(rs.getString("FOLIO"));
		CandAlumno.setCandadoId(rs.getString("CANDADO_ID"));
		CandAlumno.setfCreado(rs.getString("F_CREADO"));
		CandAlumno.setfBorrado(rs.getString("F_BORRADO"));
		CandAlumno.setUsAlta(rs.getString("US_ALTA"));
		CandAlumno.setUsBaja(rs.getString("US_BAJA"));
		CandAlumno.setComentario(rs.getString("COMENTARIO"));
		CandAlumno.setEstado(rs.getString("ESTADO"));
		CandAlumno.setTipoId(rs.getString("TIPO_ID"));
		
		return CandAlumno;
	}
}
