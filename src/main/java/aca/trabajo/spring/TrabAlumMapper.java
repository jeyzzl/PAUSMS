package aca.trabajo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TrabAlumMapper implements RowMapper<TrabAlum>{
	
public TrabAlum mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TrabAlum objeto = new TrabAlum();		
		
		objeto.setDeptId(rs.getString("DEPT_ID"));
		objeto.setCatId(rs.getString("CAT_ID"));	
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setPago(rs.getString("PAGO"));
		
		return objeto;
}
	
	
}
