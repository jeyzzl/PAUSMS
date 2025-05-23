package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpRangoEmpMapper implements RowMapper<EmpRangoEmp>{
	
	public EmpRangoEmp mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpRangoEmp objeto = new EmpRangoEmp();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setRangoId(rs.getString("RANGO_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}