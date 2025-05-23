package aca.pg.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PosArchResidenciaMapper implements RowMapper<PosArchResidencia>{
	
	public PosArchResidencia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PosArchResidencia objeto = new PosArchResidencia();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setImagen(rs.getBytes("IMAGEN"));
		
		return objeto;
	}
}