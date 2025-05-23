package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpRangoMapper implements RowMapper<EmpRango>{
	
	public EmpRango mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpRango objeto = new EmpRango();
		
		objeto.setRangoId(rs.getString("RANGO_ID"));
		objeto.setRangoNombre(rs.getString("RANGO_NOMBRE"));
		objeto.setMinimo(rs.getString("MINIMO"));
		objeto.setMaximo(rs.getString("MAXIMO"));
		objeto.setPrecioMin(rs.getString("PRECIO_MIN"));
		objeto.setPrecioMax(rs.getString("PRECIO_MAX"));
		
		return objeto;
	}
}