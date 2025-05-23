package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitAreaUsuarioMapper implements RowMapper<BitAreaUsuario> {
	public BitAreaUsuario mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitAreaUsuario objeto = new BitAreaUsuario();
		
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setCodigoPersonal	(rs.getString("CODIGO_PERSONAL"));
		
		return objeto;
	}
}