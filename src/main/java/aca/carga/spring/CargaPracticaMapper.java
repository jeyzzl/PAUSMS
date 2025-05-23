package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaPracticaMapper implements RowMapper<CargaPractica> {

	public CargaPractica mapRow(ResultSet rs, int rowNum) throws SQLException {
		CargaPractica objeto = new CargaPractica();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setEstado(rs.getString("ESTADO"));		
		
		return objeto;
	}

}
