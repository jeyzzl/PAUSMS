package aca.convenio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConArchivoMapperCorto implements RowMapper<ConArchivo> {
	public ConArchivo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConArchivo convenio = new ConArchivo();
		
		convenio.setConvenioId(rs.getString("CONVENIO_ID"));
		convenio.setFolio(rs.getString("FOLIO"));
		convenio.setNombre(rs.getString("NOMBRE"));	
		
		return convenio;
	}
}
