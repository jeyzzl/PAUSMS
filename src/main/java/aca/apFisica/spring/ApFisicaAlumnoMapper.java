package aca.apFisica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ApFisicaAlumnoMapper implements RowMapper<ApFisicaAlumno>{
	@Override
	public ApFisicaAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ApFisicaAlumno objeto = new ApFisicaAlumno();
		
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		
		return objeto;
	}
}