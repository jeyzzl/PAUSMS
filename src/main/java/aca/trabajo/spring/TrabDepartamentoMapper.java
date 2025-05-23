package aca.trabajo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TrabDepartamentoMapper implements RowMapper<TrabDepartamento>{
	
public TrabDepartamento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TrabDepartamento objeto = new TrabDepartamento();		
		
		objeto.setDeptId(rs.getString("DEPT_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));	
		objeto.setDetalles(rs.getString("DETALLES"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setCodigo_personal(rs.getString("CODIGO_PERSONAL"));
		
		return objeto;
}
	
	
}
