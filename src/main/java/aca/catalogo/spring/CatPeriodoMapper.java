package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatPeriodoMapper implements RowMapper<CatPeriodo> {
	public CatPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatPeriodo objeto = new CatPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setNombre(rs.getString("NOMBRE_PERIODO"));
		objeto.setFechaIni(rs.getString("F_INICIO"));
		objeto.setFechaFin(rs.getString("F_FINAL"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setExceptoPron(rs.getString("EXCEPTO_PRON"));
		
		return objeto;
	}
}
