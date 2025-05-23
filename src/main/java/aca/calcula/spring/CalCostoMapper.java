package aca.calcula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CalCostoMapper implements RowMapper<CalCosto> {
	public CalCosto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CalCosto objeto = new CalCosto();
		
		objeto.setCostoId(rs.getString("COSTO_ID"));
		objeto.setConceptoId(rs.getString("CONCEPTO_ID"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setImporte(rs.getString("IMPORTE"));
		objeto.setComentario(rs.getString("COMENTARIO"));
				
		return objeto;
	}
}
