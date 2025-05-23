package aca.trabajo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TrabCategoriaMapper implements RowMapper<TrabCategoria>{
	
public TrabCategoria mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TrabCategoria objeto = new TrabCategoria();		
		
		objeto.setCategoriaId(rs.getString("CAT_ID"));
		objeto.setNombreCategoria(rs.getString("NOMBRE"));	
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setDeptId(rs.getString("DEPT_ID"));
		
		return objeto;
}
	
	
}
