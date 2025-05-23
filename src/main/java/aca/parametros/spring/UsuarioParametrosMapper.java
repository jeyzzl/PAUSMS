package aca.parametros.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UsuarioParametrosMapper implements RowMapper<UsuarioParametros> {

	public UsuarioParametros mapRow(ResultSet rs, int rowNum) throws SQLException {
		UsuarioParametros objeto = new UsuarioParametros();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargas(rs.getString("CARGAS"));
		objeto.setModalidades(rs.getString("MODALIDADES"));
		objeto.setfInicio(rs.getString("F_INICIO"));
		objeto.setfFinal(rs.getString("F_FINAL"));
		
		return objeto;
	}

}
