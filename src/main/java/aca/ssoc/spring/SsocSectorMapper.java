package aca.ssoc.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SsocSectorMapper implements RowMapper<SsocSector> {

	public SsocSector mapRow(ResultSet rs, int rowNum) throws SQLException {
		SsocSector objeto = new SsocSector();
		
		objeto.setSectorId(rs.getString("Sector_Id"));
		objeto.setSectorNombre(rs.getString("Sector_Nombre"));
		
		return objeto;
	}

}
