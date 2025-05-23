package aca.internado.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IntAlumReporteMapper implements RowMapper<IntAlumReporte> {
	public IntAlumReporte mapRow(ResultSet rs, int rowNum) throws SQLException {
		IntAlumReporte objeto = new IntAlumReporte();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setReporteId(rs.getString("REPORTE_ID"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setCantidad(rs.getString("CANTIDAD"));
		objeto.setDormitorio(rs.getString("DORMITORIO"));
		
		return objeto;
	}

}
