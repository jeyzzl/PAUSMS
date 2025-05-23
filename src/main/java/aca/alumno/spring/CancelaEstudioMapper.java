package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CancelaEstudioMapper implements RowMapper<CancelaEstudio>{
	@Override
	public CancelaEstudio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CancelaEstudio objeto = new CancelaEstudio();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}