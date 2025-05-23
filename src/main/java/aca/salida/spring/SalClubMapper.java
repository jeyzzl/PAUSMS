package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalClubMapper implements RowMapper<SalClub> {

	public SalClub mapRow(ResultSet rs, int rowNum) throws SQLException {
		SalClub objeto = new SalClub();
		
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setGrupoNombre(rs.getString("GRUPO_NOMBRE"));
		objeto.setResponsable(rs.getString("RESPONSABLE"));
		objeto.setAlumnos(rs.getString("ALUMNOS"));
		
		return objeto;
	}

}
