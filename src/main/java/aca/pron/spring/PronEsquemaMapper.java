package aca.pron.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PronEsquemaMapper implements RowMapper<PronEsquema> {

	public PronEsquema mapRow(ResultSet rs, int rowNum) throws SQLException {
		PronEsquema objeto = new PronEsquema();

		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setEstrategiaId(rs.getString("ESTRATEGIA_ID"));
		objeto.setEstrategiaNombre(rs.getString("ESTRATEGIA_NOMBRE"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}

}
