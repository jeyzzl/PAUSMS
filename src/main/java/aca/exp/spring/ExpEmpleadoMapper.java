package aca.exp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExpEmpleadoMapper implements RowMapper<ExpEmpleado>{
	@Override
	public ExpEmpleado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExpEmpleado objeto = new ExpEmpleado();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setHoja(rs.getString("HOJA"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setArchivo(rs.getBytes("ARCHIVO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		
		return objeto;
	}
}