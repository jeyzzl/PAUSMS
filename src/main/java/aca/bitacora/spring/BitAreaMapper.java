package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitAreaMapper implements RowMapper<BitArea> {
	
	@Override
	public BitArea mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitArea objeto = new BitArea();
		
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setAreaNombre(rs.getString("AREA_NOMBRE"));
		objeto.setResponsable(rs.getString("RESPONSABLE"));
		objeto.setAcceso(rs.getString("ACCESO"));
		
		return objeto;
	}
}