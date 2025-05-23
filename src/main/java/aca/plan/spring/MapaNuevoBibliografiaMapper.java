package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaNuevoBibliografiaMapper implements RowMapper<MapaNuevoBibliografia>{

	public MapaNuevoBibliografia mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaNuevoBibliografia objeto = new MapaNuevoBibliografia();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setBibliografiaId(rs.getString("BIBLIOGRAFIA_ID"));
		objeto.setBibliografia(rs.getString("BIBLIOGRAFIA"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setReferencia(rs.getString("REFERENCIA"));
		
		return objeto;
	}

}