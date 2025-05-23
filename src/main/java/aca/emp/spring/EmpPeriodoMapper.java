package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpPeriodoMapper implements RowMapper<EmpPeriodo>{
	
	public EmpPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpPeriodo objeto = new EmpPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		objeto.setEstado(rs.getString("ESTADO"));	
		
		return objeto;
	}
}