package aca.ssoc.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SsocDocAlumMapper implements RowMapper<SsocDocAlum>{
	
	public SsocDocAlum mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SsocDocAlum objeto = new SsocDocAlum();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setEntregado(rs.getString("ENTREGADO"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFolio(rs.getInt("FOLIO"));
		objeto.setDocumentoId(rs.getInt("DOCUMENTO_ID"));
		objeto.setAsignacionId(rs.getInt("ASIGNACION_ID"));
		objeto.setNumHoras(rs.getInt("NUM_HORAS"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}
}