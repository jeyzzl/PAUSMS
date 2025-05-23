package aca.trabajo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TrabAsesorMapper implements RowMapper<TrabAsesor>{
	
	public TrabAsesor mapRow(ResultSet rs, int arg1) throws SQLException {
			
			TrabAsesor objeto = new TrabAsesor();		
			
			objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
			objeto.setDeptId(rs.getString("DEPT_ID"));
			objeto.setFecha(rs.getString("FECHA"));
			objeto.setStatus(rs.getString("STATUS"));
			
			return objeto;
	}	
	
}
