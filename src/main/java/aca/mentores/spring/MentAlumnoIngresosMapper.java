package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentAlumnoIngresosMapper implements RowMapper<MentAlumnoIngresos>{

	public MentAlumnoIngresos mapRow(ResultSet rs, int arg1) throws SQLException {
		MentAlumnoIngresos objeto = new MentAlumnoIngresos();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setPropios(rs.getString("PROPIOS"));
		objeto.setSemestre(rs.getString("SEMESTRE"));
		objeto.setColportaje(rs.getString("COLPORTAJE"));
		objeto.setBeca(rs.getString("BECA"));
		
		return objeto;
	}

}
