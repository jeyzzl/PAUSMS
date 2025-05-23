package aca.kardex.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KrdxMaximoMapper implements RowMapper<KrdxMaximo> {
	public KrdxMaximo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		KrdxMaximo objeto = new KrdxMaximo();		
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));		
		objeto.setEvaluadas(rs.getString("EVALUADAS"));
		objeto.setPuntos(rs.getString("PUNTOS"));
		objeto.setMaximo(rs.getString("MAXIMO"));		
		objeto.setCarreraId(rs.getString("CARRERA_ID"));				
		
		return objeto;
	}
}