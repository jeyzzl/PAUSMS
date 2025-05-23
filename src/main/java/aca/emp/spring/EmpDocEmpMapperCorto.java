package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpDocEmpMapperCorto implements RowMapper<EmpDocEmp>{
	
	public EmpDocEmp mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpDocEmp objeto = new EmpDocEmp();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setHoja(rs.getString("HOJA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}
}