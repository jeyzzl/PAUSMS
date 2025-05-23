package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchDocStatusMapper implements RowMapper<ArchDocStatus>{
	@Override
	public ArchDocStatus mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchDocStatus objeto = new ArchDocStatus();
		
		objeto.setIdDocumento(rs.getString("IDDOCUMENTO"));
		objeto.setIdStatus(rs.getString("IDSTATUS"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setValido(rs.getString("VALIDO"));
		
		return objeto;
	}
}