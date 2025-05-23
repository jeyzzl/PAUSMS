package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecVeranoMapper implements RowMapper<BecVerano> {
	
	@Override
	public BecVerano mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecVerano objeto = new BecVerano();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setDepartamento(rs.getString("DEPARTAMENTO"));
		objeto.setPuesto(rs.getString("PUESTO"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setImporte(rs.getString("IMPORTE"));
		
		return objeto;
	}
}
