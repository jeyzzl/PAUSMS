package aca.opcion.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MaestroOpcionMapper implements RowMapper<MaestroOpcion>{

	public MaestroOpcion mapRow(ResultSet rs, int arg1) throws SQLException {
		MaestroOpcion objeto = new MaestroOpcion();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setVistaEvaluar(rs.getString("VISTA_EVALUAR"));		

		return objeto;
	}

}