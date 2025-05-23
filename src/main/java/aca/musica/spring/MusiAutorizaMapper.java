package aca.musica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MusiAutorizaMapper implements RowMapper<MusiAutoriza> {

	public MusiAutoriza mapRow(ResultSet rs, int rowNum) throws SQLException {
		MusiAutoriza objeto = new MusiAutoriza();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}

}
