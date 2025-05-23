package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoProgramacionMapper implements RowMapper<CargaGrupoProgramacion>{
	
	public CargaGrupoProgramacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoProgramacion objeto = new CargaGrupoProgramacion();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}
}