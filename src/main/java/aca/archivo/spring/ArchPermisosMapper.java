package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchPermisosMapper implements RowMapper<ArchPermisos>{
	@Override
	public ArchPermisos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchPermisos objeto = new ArchPermisos();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setUsuarioAlta(rs.getString("USUARIO_ALTA"));
		objeto.setUsuarioBaja(rs.getString("USUARIO_BAJA"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaLim(rs.getString("FECHA_LIM"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		
		return objeto;
	}
}