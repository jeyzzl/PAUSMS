package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatTipoCursoMapper implements RowMapper<CatTipoCurso> {
	public CatTipoCurso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatTipoCurso objeto = new CatTipoCurso();
		
		objeto.setTipoCursoId(rs.getString("TIPOCURSO_ID"));
		objeto.setNombreTipoCurso(rs.getString("NOMBRE_TIPOCURSO"));
		objeto.setCorto(rs.getString("CORTO"));
		
		return objeto;
	}
}
