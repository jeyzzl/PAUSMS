package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinPermisoMapper implements RowMapper<FinPermiso> {

	public FinPermiso mapRow(ResultSet rs, int arg1) throws SQLException {
		FinPermiso objeto = new FinPermiso();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFInicio(rs.getString("F_INICIO"));
		objeto.setFLimite(rs.getString("F_LIMITE"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setComentario(rs.getString("COMENTARIO"));

		return objeto;
	}

}
