package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoEjeMapper implements RowMapper<CargaGrupoEje>{
	
	public CargaGrupoEje mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoEje objeto = new CargaGrupoEje();
		
		objeto.setEjeId(rs.getString("EJE_ID"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		
		return objeto;
	}
}