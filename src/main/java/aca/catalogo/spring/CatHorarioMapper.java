package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatHorarioMapper implements RowMapper<CatHorario> {
	public CatHorario mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatHorario objeto = new CatHorario();
		
		objeto.setHorarioId(rs.getString("HORARIO_ID"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setAcceso(rs.getString("ACCESO"));
				
		return objeto;
	}
}
