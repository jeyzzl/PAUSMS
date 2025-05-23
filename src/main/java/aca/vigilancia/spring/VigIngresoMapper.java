package aca.vigilancia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VigIngresoMapper implements RowMapper<VigIngreso>{

	public VigIngreso mapRow(ResultSet rs, int arg1) throws SQLException {
		VigIngreso objeto = new VigIngreso();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCodigoId(rs.getString("CODIGO_ID"));
		objeto.setResId(rs.getString("RES_ID"));
		objeto.setDormi(rs.getString("DORMI"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}

}
