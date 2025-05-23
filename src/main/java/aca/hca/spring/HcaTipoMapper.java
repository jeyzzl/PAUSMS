package aca.hca.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HcaTipoMapper implements RowMapper<HcaTipo> {
	public HcaTipo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		HcaTipo objeto = new HcaTipo();		
		objeto.setTipoId(rs.getString("TIPO_ID"));
		objeto.setTipoNombre(rs.getString("TIPO_NOMBRE"));
		objeto.setOrden(rs.getString("ORDEN"));		
		
		return objeto;
	}
}