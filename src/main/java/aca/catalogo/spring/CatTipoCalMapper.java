package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatTipoCalMapper implements RowMapper<CatTipoCal> {
	public CatTipoCal mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatTipoCal objeto = new CatTipoCal();
		
		objeto.setTipoCalId(rs.getString("TIPOCAL_ID"));
		objeto.setNombreTipoCal(rs.getString("NOMBRE_TIPOCAL"));
		objeto.setNombreCorto(rs.getString("NOMBRE_CORTO"));
		
		return objeto;
	}
}
