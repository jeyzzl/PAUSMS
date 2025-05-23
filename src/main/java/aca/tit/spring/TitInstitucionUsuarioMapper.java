package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitInstitucionUsuarioMapper implements RowMapper<TitInstitucionUsuario> {
	public TitInstitucionUsuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		TitInstitucionUsuario objeto = new TitInstitucionUsuario();
		
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}

}
