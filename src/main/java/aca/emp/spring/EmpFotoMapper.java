package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpFotoMapper implements RowMapper<EmpFoto> {

	public EmpFoto mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmpFoto objeto = new EmpFoto();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setFoto(rs.getBytes("FOTO"));
		
		return objeto;
	}

}
