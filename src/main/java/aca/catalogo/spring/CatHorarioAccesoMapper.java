package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatHorarioAccesoMapper implements RowMapper<CatHorarioAcceso> {
	public CatHorarioAcceso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatHorarioAcceso objeto = new CatHorarioAcceso();
		
		objeto.setHorarioId(rs.getString("HORARIO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
				
		return objeto;
	}
}
