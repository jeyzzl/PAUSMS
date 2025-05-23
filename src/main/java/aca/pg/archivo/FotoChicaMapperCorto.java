package aca.pg.archivo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FotoChicaMapperCorto implements RowMapper<FotoChica>{
	
	public FotoChica mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FotoChica objeto = new FotoChica();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));		
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}
}