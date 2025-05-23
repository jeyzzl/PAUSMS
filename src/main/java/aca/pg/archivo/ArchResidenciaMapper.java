package aca.pg.archivo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchResidenciaMapper implements RowMapper<ArchResidencia>{
	
	public ArchResidencia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchResidencia objeto = new ArchResidencia();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setImagen(rs.getBytes("IMAGEN"));
		
		return objeto;
	}
}