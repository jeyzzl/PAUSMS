package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentLogMapper implements RowMapper<MentLog>{

	public MentLog mapRow(ResultSet rs, int arg1) throws SQLException {
		MentLog objeto = new MentLog();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setMentorId(rs.getString("MENTOR_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setTab(rs.getString("TAB"));
		
		return objeto;
	}

}
