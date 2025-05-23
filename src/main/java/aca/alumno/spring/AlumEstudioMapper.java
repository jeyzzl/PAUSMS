package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumEstudioMapper implements RowMapper<AlumEstudio>{
	@Override
	public AlumEstudio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumEstudio objeto = new AlumEstudio();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setId(rs.getString("ID"));
		objeto.setTitulo(rs.getString("TITULO"));
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setCompleto(rs.getString("COMPLETO"));
		objeto.setInicio(rs.getString("INICIO"));
		objeto.setFin(rs.getString("FIN"));
		objeto.setDependencia(rs.getString("DEPENDENCIA"));
		objeto.setConvalida(rs.getString("CONVALIDA"));
		
		return objeto;
	}
}