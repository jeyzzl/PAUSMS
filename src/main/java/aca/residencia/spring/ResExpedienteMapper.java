package aca.residencia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResExpedienteMapper implements RowMapper<ResExpediente>{
	
	public ResExpediente mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ResExpediente objeto = new ResExpediente();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}
}